<?php
header('Content-type: application/json; charset=utf-8');
$jsonResult = new \stdClass();
session_start();
if(!isset($_SESSION["loggedin_api"]) && !$_SESSION["loggedin_api"] === true){
    $jsonResult->success=false;
    $jsonResult->reason="Operation not allowed, user is not authenticated";
    http_response_code(403);
    die(json_encode($jsonResult));
}
require_once "../../../config.php";
include "../../../functions.php";
if($_SERVER["REQUEST_METHOD"] == "POST"){
    $data = json_decode(file_get_contents('php://input'), true);
    if($data==null){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, input is not of type JSON";
        http_response_code(400);
        die(json_encode($jsonResult));
    }
    if(empty($data["oldPassword"])){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, oldPassword can't be empty";
        http_response_code(400);
        die(json_encode($jsonResult));
    }
    if(empty($data["newPassword"])){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, newPassword can't be empty";
        http_response_code(400);
        die(json_encode($jsonResult));
    }elseif(strlen(trim($data["newPassword"])) < 6){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, newPassword must contain at least 6 characters";
        http_response_code(400);
        die(json_encode($jsonResult));
    }
    $sql = "SELECT password FROM users WHERE id = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "s", $param_id);
        $param_id = $_SESSION["id"];
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            mysqli_stmt_bind_result($stmt, $hashed_password);
            if(mysqli_stmt_fetch($stmt)){
                if(password_verify($data["oldPassword"], $hashed_password)){
                    $sql = "UPDATE users SET password = ? WHERE id = ?";
                    if($stmt = mysqli_prepare($link, $sql)){
                        mysqli_stmt_bind_param($stmt, "ss", $param_password, $param_id);
                        $param_id = $_SESSION["id"];
                        $param_password = password_hash($data['newPassword'], PASSWORD_DEFAULT);
                        if(mysqli_stmt_execute($stmt)){
                            $jsonResult->success=true;
                            http_response_code(200);
                            die(json_encode($jsonResult));
                        }else{
                            $jsonResult->success=false;
                            $jsonResult->reason="Database error, unknown server error occured while saving new password.";
                            http_response_code(500);
                            die(json_encode($jsonResult));
                        }
                    }
                }else{
                    $jsonResult->success=false;
                    $jsonResult->reason="Verification error, oldPassword doesnt match.";
                    http_response_code(403);
                    die(json_encode($jsonResult));
                }
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Database error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
        }
    }
}else{
    $jsonResult->success=false;
    $jsonResult->reason="Operation not allowed, request method (".$_SERVER["REQUEST_METHOD"].") not allowed";
    http_response_code(405);
    die(json_encode($jsonResult));
}