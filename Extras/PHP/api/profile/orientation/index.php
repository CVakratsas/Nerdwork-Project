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
if($_SERVER["REQUEST_METHOD"] == "PUT"){
    $data = json_decode(file_get_contents('php://input'), true);
    if($data==null){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, input is not of type JSON";
        http_response_code(400);
        die(json_encode($jsonResult));
    }
    if($data["orientation"]===null){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, orientation can't be null";
        http_response_code(400);
        die(json_encode($jsonResult));
    }elseif(!is_int($data["orientation"])){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, orientation must be of type int.";
        http_response_code(400);
        die(json_encode($jsonResult));
    }elseif($data["orientation"]!=0 && $data["orientation"]!=1){
        $jsonResult->success=false;
        $jsonResult->reason="Validation error, orientation must be 1 or 0";
        http_response_code(400);
        die(json_encode($jsonResult));
    }
    $sql = "UPDATE users SET orientation = ? WHERE id = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "is", $param_orientation, $param_id);
        $param_orientation = $data["orientation"];
        $param_id = $_SESSION["id"];
        if(!mysqli_stmt_execute($stmt)){
                $jsonResult->success=false;
                $jsonResult->reason="Database error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }else{
                $jsonResult->success=true;
                http_response_code(200);
                die(json_encode($jsonResult));
            }
    }else{
        $jsonResult->success=false;
        $jsonResult->reason="Database error, unknown server error occured.";
        http_response_code(500);
        die(json_encode($jsonResult));
    }
}else{
    $jsonResult->success=false;
    $jsonResult->reason="Operation not allowed, request method (".$_SERVER["REQUEST_METHOD"].") not allowed";
    http_response_code(405);
    die(json_encode($jsonResult));
}