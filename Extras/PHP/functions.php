<?php

function getProfessorBio($professorId){
    require "config.php";
    $sql = "SELECT bio FROM users WHERE associatedProfessor = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "i", $param_professorId);
        $param_professorId = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) != 0){
                mysqli_stmt_bind_result($stmt, $bio);
                mysqli_stmt_fetch($stmt);
                return $bio;
            }else{
                return "";
            }
            
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}

function getProfessorRating($professorId){
    require "config.php";
    $sql = "SELECT rating FROM professors WHERE id = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "i", $param_professorId);
        $param_professorId = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) != 0){
                mysqli_stmt_bind_result($stmt, $rating);
                mysqli_stmt_fetch($stmt);
                return floatval($rating);
            }else{
                return -1;
            }
            
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}

function getSubjectRating($subjectId){
    require "config.php";
    $sql = "SELECT rating FROM subjects WHERE id = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "s", $param_subjectId);
        $param_subjectId = $subjectId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) != 0){
                mysqli_stmt_bind_result($stmt, $rating);
                mysqli_stmt_fetch($stmt);
                return floatval($rating);
            }else{
                return -1;
            }
            
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}

function getProfessorDetailedVotes($professorId){
    require "config.php";
    $sql = "SELECT SUM(rating), COUNT(rating) FROM ratings WHERE professorId = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "s", $param_professorId);
        $param_professorId = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            mysqli_stmt_bind_result($stmt, $sum, $count);
            if(mysqli_stmt_fetch($stmt)){
                $outResult["sum"] = $sum;
                $outResult["count"] = $count;
                return $outResult;
            }else{
              $jsonResult->success=false;
              $jsonResult->reason="Database error, unknown server error occured.";
              http_response_code(500);
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

function getSubjectDetailedVotes($subjectId){
    require "config.php";
    $sql = "SELECT SUM(rating), COUNT(rating) FROM ratings WHERE subjectId = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "s", $param_subjectId);
        $param_subjectId = $subjectId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            mysqli_stmt_bind_result($stmt, $sum, $count);
            if(mysqli_stmt_fetch($stmt)){
                $outResult["sum"] = $sum;
                $outResult["count"] = $count;
                return $outResult;
            }else{
              $jsonResult->success=false;
              $jsonResult->reason="Database error, unknown server error occured.";
              http_response_code(500);
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

function getMyProfessorRating($professorId, $userId){
    require "config.php";
    $sql = "SELECT rating FROM ratings WHERE userId = ? AND professorId = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "si", $param_id, $param_professorId);
        $param_id = $userId;
        $param_professorId = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) != 0){
                mysqli_stmt_bind_result($stmt, $rating);
                mysqli_stmt_fetch($stmt);
                return intval($rating);
            }else{
                return -1;
            }
            
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}

function doesProfessorExist($professorId){
    require "config.php";
     $sql = "SELECT rating FROM professors WHERE id = ?";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "i", $param_professorid);
            
            // Set parameters
            $param_professorid = $professorId;
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                return !(mysqli_stmt_num_rows($stmt) == 0);
                
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
        }
}

function getBookedTimestamps($professorId){
    require "config.php";
    $sql = "SELECT date FROM appointments WHERE (professorId = ? AND status != 2 AND date>=current_timestamp())";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "i", $param_id);
        $param_id = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            mysqli_stmt_bind_result($stmt, $timestamp);
            $index= 0;
            $outTimestamps = [];
            while(mysqli_stmt_fetch($stmt)){
                $outTimestamps[$index] = strtotime($timestamp);
                $index++;
            }
            return $outTimestamps;
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }else{
        $jsonResult->success=false;
        $jsonResult->reason="Database error, unknown server error occured.";
        http_response_code(500);
        die(json_encode($jsonResult));
    }
}
function getMyAppointments(){
    require "config.php";
    if($_SESSION["type"]!=1){
        $sql = "SELECT * FROM appointments WHERE (studentId = ? AND date>=current_timestamp())";
        $param_type = "s";
        $id = $_SESSION["id"];
    }else{
        $sql = "SELECT * FROM appointments WHERE (professorId = ? AND date>=current_timestamp())";
        $param_type = "i";
        $id = $_SESSION["associatedProfessor"];
    }
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, $param_type, $param_id);
        $param_currentTime = time();
        $param_id = $id;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            mysqli_stmt_bind_result($stmt, $appointmentId, $studentId, $professorId, $date, $status, $created_at);
            $index= 0;
            while(mysqli_stmt_fetch($stmt)){
                $outAppointments[$index]["appointmentId"] = $appointmentId;
                $outAppointments[$index]["studentId"] = $studentId;
                $outAppointments[$index]["professorId"] = $professorId;
                $outAppointments[$index]["date"] = strtotime($date);
                $outAppointments[$index]["status"] = $status;
                $outAppointments[$index]["created_at"] = $created_at;
                $index++;
            }
            return $outAppointments;
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}
function getMySubjectRating($subjectId, $userId){
    require "config.php";
    $sql = "SELECT rating FROM ratings WHERE userId = ? AND subjectId = ?";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "ss", $param_id, $param_subjectId);
        $param_id = $userId;
        $param_subjectId = $subjectId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) != 0){
                mysqli_stmt_bind_result($stmt, $rating);
                mysqli_stmt_fetch($stmt);
                return intval($rating);
            }else{
                return -1;
            }
            
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
    }
}
function getAvailabilityHours($day, $professorId){
    require "config.php";
    $sql = "SELECT startHour, endHour FROM availabilityDates WHERE (professorId = ? AND day = ?)";
    if($stmt = mysqli_prepare($link, $sql)){
        mysqli_stmt_bind_param($stmt, "ii", $param_profid, $param_day);
        $param_day = $day;
        $param_profid = $professorId;
        if(mysqli_stmt_execute($stmt)){
            mysqli_stmt_store_result($stmt);
            if(mysqli_stmt_num_rows($stmt) == 0){
                return false;
            }else{
                mysqli_stmt_bind_result($stmt, $startHour, $endHour);
                if(mysqli_stmt_fetch($stmt)){
                    $outHours["startHour"] = $startHour;
                    $outHours["endHour"] = $endHour;
                    return $outHours;
                }else{
                    $jsonResult->success=false;
                    $jsonResult->reason="Database error, unknown server error occured.";
                    http_response_code(500);
                    die(json_encode($jsonResult));
                }
            }
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Database error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
        
    }
}
function getUserId($username){
    require "config.php";
     $sql = "SELECT id FROM users WHERE username = ?";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "s", $param_username);
            
            // Set parameters
            $param_username = $username;
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                
                if(mysqli_stmt_num_rows($stmt) == 0){
                    return false;
                }else{
                    mysqli_stmt_bind_result($stmt, $userId);
                    if(mysqli_stmt_fetch($stmt)){
                        return  $userId;
                    }else{
                        $jsonResult->success=false;
                        $jsonResult->reason="Register error, unknown server error occured.";
                        http_response_code(500);
                        die(json_encode($jsonResult));
                    }
                    
                }
                
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
        }
}
function doesSubjectExist($subjectId){
     require "config.php";
     $sql = "SELECT rating FROM subjects WHERE id = ?";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "s", $param_username);
            
            // Set parameters
            $param_username = $subjectId;
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                
                if(mysqli_stmt_num_rows($stmt) == 0){
                    return false;
                }else{
                    return true;
                }
                
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
        }else{
            $jsonResult->success=false;
            $jsonResult->reason="Register error, unknown server error occured.";
            http_response_code(500);
            die(json_encode($jsonResult));
        }
}
function getIncomingFriendRequests(){
    require "config.php";
     $sql = "SELECT requesterId FROM friendrequest WHERE (receiverId = ? AND isDeleted = 0)";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "s", $param_id);
            
            // Set parameters
            $param_id = trim($_SESSION["id"]);
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                mysqli_stmt_bind_result($stmt, $userId);
                $index = 0;
                $outFriends = [];
                while(mysqli_stmt_fetch($stmt)){
                    $outFriends[$index] = $userId;
                    $index = $index+1;
                }
                return $outFriends;
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
                
        }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
        
}
function getOutgoingFriendRequests(){
     require "config.php";
     $sql = "SELECT receiverId FROM friendrequest WHERE (requesterId = ? AND isDeleted = 0)";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "s", $param_id);
            
            // Set parameters
            $param_id = trim($_SESSION["id"]);
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                mysqli_stmt_bind_result($stmt, $userId);
                $index = 0;
                $outFriends = [];
                while(mysqli_stmt_fetch($stmt)){
                    $outFriends[$index] = $userId;
                    $index = $index+1;
                }
                return $outFriends;
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
                
        }
}
function getFriends(){
    require "config.php";
     $sql = "SELECT userId1, userId2 FROM friends WHERE (userId1 = ? or userId2 = ?)";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "ss", $param_id, $param_id);
            
            // Set parameters
            $param_id = trim($_SESSION["id"]);
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                mysqli_stmt_bind_result($stmt, $userId1, $userId2);
                $index = 0;
                $outFriends = [];
                while(mysqli_stmt_fetch($stmt)){
                    if($userId1!=$param_id){
                        $outFriends[$index] = $userId1;
                    }else{
                        $outFriends[$index] = $userId2;
                    }
                    
                    $index = $index+1;
                }
                return $outFriends;
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
                
        }
        
}
function getEnrollments(){
    require "config.php";
     $sql = "SELECT subjectId FROM enrollments WHERE userId = ?";
        
        if($stmt = mysqli_prepare($link, $sql)){
            // Bind variables to the prepared statement as parameters
            mysqli_stmt_bind_param($stmt, "s", $param_id);
            
            // Set parameters
            $param_id = trim($_SESSION["id"]);
            
            // Attempt to execute the prepared statement
            if(mysqli_stmt_execute($stmt)){
                /* store result */
                mysqli_stmt_store_result($stmt);
                mysqli_stmt_bind_result($stmt, $subject);
                $index = 0;
                $outEnrollements = [];
                while(mysqli_stmt_fetch($stmt)){
                    $outEnrollments[$index] = $subject;
                    $index = $index+1;
                }
                return $outEnrollments;
            }else{
                $jsonResult->success=false;
                $jsonResult->reason="Register error, unknown server error occured.";
                http_response_code(500);
                die(json_encode($jsonResult));
            }
                
        }
        
}
function getprivacypolicy(){
    echo'<a style="color:white"><p><strong>A. Εισαγωγή</strong></p>
<p style="padding-left: 40px;">1. Το απόρρητο των επισκεπτών του ιστοτόπου μας, είναι πολύ σημαντικό για εμάς και είμαστε αφοσιωμένοι στην προστασία του. Η πολιτική μας εξηγεί πως θα χρησιμοποιήσουμε τις προσωπικές σας πληροφορίες.<br>
2. Η συναίνεση σας στη χρήση των cookies σε συμφωνία με τους όρους της πολιτικής μας κατά την πρώτη σας επίσκεψη στον ιστότοπο, μας επιτρέπει την χρήση cookies κάθε φορά που επισκέπτεστε τον ιστότοπο μας.</p>
<p><strong>B. Εύσημα</strong><br>
Αυτό το έγγραφο δημιουργήθηκε με την χρήση προτύπου από την SEQ Legal (seqlegal.com) και τροποποιήθηκε από την Website Planet (<a href="https://www.websiteplanet.com/">www.websiteplanet.com</a><a style="color:white">)</p>
<p><strong>C. Συλλογή προσωπικών πληροφοριών</strong></p>
<p>Οι παρακάτω τύποι προσωπικών πληροφοριών μπορούν να συλλεγούν, να αποθηκευτούν και να χρησιμοποιηθούν:</p>
<ol style="padding-left: 40px;">
<li>Πληροφορίες σχετικές με τον υπολογιστή σας μεταξύ των οποίων η διεύθυνση IP σας, η γεωγραφική σας τοποθεσία ο τύπος και η έκδοση του περιηγητή σας και το λειτουργικό σας σύστημα.</li>
<li>Πληροφορίες σχετικά με τις επισκέψεις σας και τη χρήση του ιστοτόπου περιλαμβάνοντας την εξωτερική πηγή, τη διάρκεια της επίσκεψης, της προβολές σελίδων και τη διαδρομή πλοήγησης στον ιστότοπο;</li>
<li>Πληροφορίες όπως η διεύθυνση email σας, την οποία εισάγετε κατά την εγγραφή στον ιστότοπο.</li>
<li>Πληροφορίες που εισάγετε κατά τη δημιουργία του προφίλ σας στον ιστότοπο μας—για παράδειγμα, το όνομα σας, οι φωτογραφίες προφίλ, το φύλο, η ημερομηνία γέννησης, η προσωπική σας κατάσταση, τα ενδιαφέροντα και τα χόμπι σας, πληροφορίες σχετικά με την εκπαίδευση σας και πληροφορίες σχετικά με την απασχόληση σας.</li>
<li>Πληροφορίες, όπως το όνομα και το email σας, τις οποίες εισάγετε κατά την ρύθμιση των συνδρομών σας και για την αποστολή των emails και/η των ενημερωτικών δελτίων.</li>
<li>&nbsp;Πληροφορίες που εισάγετε κατά τη χρήση υπηρεσιών στον ιστότοπο μας.</li>
<li>Πληροφορίες που προκύπτουν κατά τη χρήση του ιστότοπου, όπως πότε, πόσο συχνά και κάτω από ποιες συνθήκες το χρησιμοποιείτε.</li>
<li>&nbsp;Πληροφορίες που κοινοποιείτε στον ιστότοπο με την πρόθεση της δημοσίευσης στο διαδίκτυο, οι οποίες περιλαμβάνουν το όνομα χρήστη σας τις πληροφορίες προφίλ σας και το περιεχόμενο των δημοσιεύσεών σας.</li>
<li>Πληροφορίες που περιέχονται σε οποιαδήποτε επικοινωνία σας με εμάς μέσω email ή μέσω του ιστοτόπου, περιλαμβάνοντας το περιεχόμενο επικοινωνίας και τα μεταδεδομένα (metadata).</li>
<li>Οποιαδήποτε άλλη προσωπική πληροφορία μας στέλνετε.</li>
</ol>
<p>Προτού αποκαλύψετε οποιαδήποτε προσωπικές πληροφορίες κάποιου τρίτου ατόμου, θα πρέπει να έχετε τη συγκατάθεση αυτού του ατόμου σχετικά με την κοινοποίηση και την επεξεργασία των προσωπικών πληροφοριών του, σύμφωνα με την παρούσα πολιτική.</p>
<p><strong>D. Χρήση των προσωπικών σας πληροφοριών</strong></p>
<p>Οι προσωπικές πληροφορίες που υποβάλλονται σε εμάς χρησιμοποιούνται για τους σκοπούς που ορίζονται από αυτή την πολιτική στις σχετικές σελίδες του ιστοτόπου. Μπορούμε να χρησιμοποιήσουμε τις προσωπικές πληροφορίες για τους παρακάτω σκοπούς:</p>
<ol style="padding-left: 40px;">
<li>διαχείριση του ιστοτόπου και της επιχείρησης μας</li>
<li>προσωποποιημένη διαμόρφωση του ιστοτόπου για εσάς</li>
<li>&nbsp;ενεργοποίηση της χρήσης των διαθέσιμων υπηρεσιών σας στον ιστότοπο μας</li>
<li>αποστολή των αγαθών που αγοράζετε μέσω του ιστοτόπου μας</li>
<li>παροχή υπηρεσιών που αγοράζετε μέσω του ιστοτόπου μας</li>
<li>&nbsp;αποστολή κινήσεων, τιμολογίων και υπενθυμίσεων πληρωμής σε εσάς, καθώς και συλλογή πληρωμών από εσάς</li>
<li>&nbsp;αποστολή μη διαφημιστικών επικοινωνιών</li>
<li>αποστολή ειδοποιήσεων μέσω email σχετικά με τα αιτήματά σας</li>
<li>&nbsp;αποστολή του ενημερωτικού δελτίου μας μέσω email αν το έχετε ζητήσει (μπορείτε να μας ενημερώσετε ανά πάσα στιγμή αν πλέον δεν επιθυμείτε το ενημερωτικό δελτίο)</li>
<li>αποστολή διαφημιστικών επικοινωνιών, σχετικών με την επιχείρηση σας ή τις επιχειρήσεις συγκεκριμένων τρίτων μερών οι οποίες πιστεύουμε ότι μπορεί να σας ενδιαφέρουν, μέσω κοινοποιήσεων ή, αν έχετε συμφωνήσει σε αυτό, μέσω by email ή παρόμοιας τεχνολογίας (μπορείτε να μας ενημερώσετε ανά πάσα στιγμή αν πλέον δεν επιθυμείτε διαφημιστικές επικοινωνίες)</li>
<li>παροχή στατιστικών δεδομένων των χρηστών μας σε τρίτα μέρη (τα οποία δεν θα είναι σε θέση να ταυτοποιήσουν κανένα χρήστη από αυτές τις πληροφορίες)</li>
<li>&nbsp;διαχείριση αιτημάτων και παραπόνων που υποβάλατε οι ίδιοι ή σχετίζονται με τον ιστότοπο σας</li>
<li>&nbsp;διατήρηση της ασφάλειας του ιστοτόπου και πρόληψη κατά οποιασδήποτε απάτης</li>
<li>επιβεβαίωση της συμμόρφωσης με τους όρους και προϋποθέσεις παροχής του ιστοτόπου μας (περιλαμβάνοντας και τη επιτήρηση προσωπικών μηνυμάτων που διακινούνται μέσω της ιδιωτικής διαδικτυακής υπηρεσίας μηνυμάτων μας) και</li>
<li>&nbsp;άλλες χρήσεις.</li>
</ol>
<p>Αν υποβάλλετε προσωπικές πληροφορίες προς δημοσίευση στον ιστότοπο μας, θα δημοσιεύσουμε ή θα χρησιμοποιήσουμε αυτές τις πληροφορίες σε συμφωνία με την αδειοδότηση σας.</p>
<p>Οι ρυθμίσεις απορρήτου σας μπορούν να χρησιμοποιηθούν για να περιορίσουν τη δημοσίευση των πληροφοριών σας στον ιστότοπο μας και μπορούν να διαμορφωθούν χρησιμοποιώντας τον έλεγχο απορρήτου στον ιστότοπο.</p>
<p>Δεν θα διοχετεύσουμε τις προσωπικές σας πληροφορίες χωρίς τη συναίνεση σας σε κανένα τρίτο μέρος ή σε οποιαδήποτε εμπορικό τμήμα τρίτου μέρους.</p>
<p><strong>E. Κοινοποίηση προσωπικών πληροφοριών</strong></p>
<p>Μπορούμε να κοινοποιήσουμε τις προσωπικές σας πληροφορίες σε οποιονδήποτε από τους υπαλλήλους μας, τα στελέχη, τους ασφαλιστές, ή τους επαγγελματικούς συμβούλους, τους εκπροσώπους τους προμηθευτές ή τους υπεργολάβους μας όπως απαιτείται για τους σκοπούς που αναφέρει η παρούσα πολιτική.</p>
<p>Μπορούμε να κοινοποιήσουμε τις προσωπικές σας πληροφορίες σε οποιοδήποτε μέλος του ομίλου εταιρειών μας (αυτό σημαίνει τις θυγατρικές μας εταιρείες, την ελέγχου εταιρεία του χαρτοφυλακίου μας και τις θυγατρικές της) όπως απαιτείται για τους σκοπούς που αναφέρει η παρούσα πολιτική.</p>
<p>Μπορούμε να κοινοποιήσουμε τις προσωπικές μας πληροφορίες:</p>
<ol style="padding-left: 40px;">
<li>στο μέτρο που απαιτείται βάσει νόμου</li>
<li>σε σχέση με οποιαδήποτε τρέχουσα ή μελλοντική νομική διαδικασία</li>
<li>&nbsp;με σκοπό την εδραίωση, άσκηση ή υπεράσπιση των νομίμων δικαιωμάτων μας (μεταξύ των οποίων η παροχή πληροφοριών σε τρίτους με σκοπό την πρόληψη απάτης και την ελάττωση του πιστωτικού κινδύνου)</li>
<li>στον αγοραστή (ή πιθανού αγοραστή) οποιουδήποτε αγαθού της επιχείρισης το οποίο πουλάμε (ή σκεφτόμαστε να πουλήσουμε) και</li>
<li>σε οποιονδήποτε βάσιμα πιστεύουμε ότι μπορεί να ανήκει στην δικαστική η οποιοδήποτε άλλη αρμόδια αρχή σχετικά με την κοινοποίηση πληροφοριών στην περίπτωση που θεωρούμε ότι η αντίστοιχη αρχή θα απαιτήσει την κοινοποίηση των συγκεκριμένων προσωπικών πληροφοριών.</li>
</ol>
<p>Επίσης όπως αναφέρεται στην παρούσα πολιτική, δεν θα παρέχουμε τις προσωπικές σας πληροφορίες σε τρίτα μέρα.</p>
<p><strong>F. Διεθνείς μεταφορές δεδομένων</strong></p>
<ol style="padding-left: 40px;">
<li>Οι πληροφορίες που συλλέγουμε μπορεί να αποθηκευτούν να επεξεργαστούν και να μετακινηθούν μεταξύ οποιωνδήποτε χωρών στις οποίες λειτουργούμε έτσι ώστε να μπορούμε να χρησιμοποιήσουμε σύμφωνα με την παρούσα πολιτική.</li>
<li>Οι πληροφορίες που συλλέγουμε μπορούν να μεταφερθούν στις ακόλουθες χώρες που δεν διαθέτουν αντίστοιχη νομοθεσία προστασίας προσωπικών δεδομένων με αυτή που εφαρμόζεται στην Ευρωπαϊκή Οικονομική Περιοχή: οι ΗΠΑ, η Ρωσία η Ιαπωνία η Κίνα και η Ινδία.</li>
<li>&nbsp;Οι προσωπικές πληροφορίες που δημοσιεύετε στον ιστότοπο μας ή υποβάλλετε προς δημοσίευση στον ιστότοπο μας, δύνανται είναι διαθέσιμες μέσω διαδικτύου σε όλο τον κόσμο. Δεν μπορούμε να αποτρέψουμε τη χρήση ή τη λανθασμένη χρήση τέτοιων πληροφοριών από τρίτους.</li>
<li>Συμφωνείτε ρητά στη μεταφορά των προσωρινών δεδομένων η οποία περιγράφεται στην ενότητα F.</li>
</ol>
<p><strong>G. Διατήρηση προσωπικών δεδομένων</strong></p>
<ol style="padding-left: 40px;">
<li>Η Ενότητα G ορίζει την διαδικασία και την πολιτική διατήρησης προσωπικών δεδομένων, που έχει σχεδιαστεί για να διασφαλίζει τη συμμόρφωση με τις νομικές μας υποχρεώσεις σχετικά με τη διατήρηση και τη διαγραφή των προσωπικών δεδομένων.</li>
<li>Προσωπικά δεδομένα που επεξεργαζόμαστε για οποιοδήποτε σκοπό δεν θα διατηρούνται για περισσότερο από όσο διάστημα είναι απαραίτητο για αυτό το σκοπό.</li>
<li>Χωρίς προκατάληψη στο άρθρο G-2, συνήθως διαγράφουμε προσωπικά δεδομένα που εμπίπτουν στην παρακάτω κατηγορίες και την αντίστοιχη ημερομηνία/ώρα
<ol>
<li style="list-style-type: none;">
<ol>
<li style="list-style-type: none;">
<ol>
<li>προσωπικά δεδομένα τύπου θα διαγράφονται {ΕΙΣΑΓΕΤΕ ΗΜΕΡΟΜΗΝΙΑ/ΩΡΑ} και</li>
<li>&nbsp;{ΕΙΣΑΓΕΤΕ ΕΠΙΠΛΕΟΝ ΗΜΕΡΟΜΗΝΙΑ/ΩΡΑ}.</li>
</ol>
</li>
</ol>
</li>
</ol>
</li>
<li>Παρά τις υπόλοιπες διατάξεις που προβλέπει η Ενότητα G, θα διατηρήσουμε έγγραφα (μεταξύ των οποίων και ηλεκτρονικά έγγραφα) που περιέχουν προσωπικά δεδομένα</li>
<li style="list-style-type: none;">
<ol>
<li style="list-style-type: none;">
<ol>
<li style="list-style-type: none;">
<ol style="padding-left: 40px;">
<li>στο βαθμό που απαιτείται από το νόμο.</li>
<li>αν θεωρούμε ότι τα έγγραφα μπορεί να σχετίζονται με οποιαδήποτε νομική διαδικασία σε ισχύ και</li>
<li>με σκοπό την εδραίωση, άσκηση ή υπεράσπιση των νομίμων δικαιωμάτων μας (μεταξύ των οποίων η παροχή πληροφοριών σε τρίτους με σκοπό την πρόληψη απάτης και την ελάττωση του πιστωτικού κινδύνου)</li>
</ol>
</li>
</ol>
</li>
</ol>
</li>
</ol>
<p><strong>H. Ασφάλεια των προσωπικών δεδομένων σας</strong></p>
<ol style="padding-left: 40px;">
<li>Θα λάβουμε τις απαιτούμενες προφυλάξεις σε τεχνικό και οργανωτικό επίπεδο για να αποτρέψουμε την απώλεια, την κακή χρήση η την αλλοίωση των προσωπικών σας δεδομένων.</li>
<li>Θα αποθηκεύσουμε όλα τα προσωπικά δεδομένα που παρέχετε στους ασφαλείς (προστατευμένους με κωδικό- και τείχος προστασίας) διακομιστές.</li>
<li>Όλες οι ηλεκτρονικές χρηματικές συναλλαγές που διενεργούνται στον ιστότοπο μας προστατεύονται από την τεχνολογία κρυπτογράφησης μας.</li>
<li>Αποδέχεστε ότι η μετάδοση πληροφοριών στο διαδίκτυο είναι εγγενώς μη ασφαλής και δεν μπορούμε να εγγυηθούμε την ασφάλεια των δεδομένων που διακινούνται μέσω διαδικτύου.</li>
<li>Είστε υπεύθυνοι να διατηρήσετε εμπιστευτικό τον κωδικού που χρησιμοποιείτε, δεν θα σας ζητήσουμε τον κωδικό σας (παρά μόνο όταν συνδέεστε στον ιστότοπο μας).</li>
</ol>
<p><strong>I. Τροποποιήσεις</strong></p>
<p>Μπορεί να ενημερώνουμε την πολιτικής μας κατά καιρούς δημοσιεύοντας μία νέα έκδοση στον ιστότοπο μας κατά καιρούς. Θα πρέπει περιστασιακά να ελέγχετε τη σελίδα ώστε να σιγουρευτείτε ότι κατανοείτε οποιαδήποτε αλλαγή στην πολιτική μας. Μπορεί να σας ενημερώσουμε για αλλαγές στην πολιτική μας μέσω email ή μέσω ιδιωτικού συστήματος ανταλλαγής μηνυμάτων στον ιστότοπο μας.</p>
<p><strong>J. Τα δικαιώματα σας</strong></p>
<p>Μπορείτε να μας ζητήσετε να σας δώσουμε, οποιαδήποτε πληροφορία σας διατηρούμε; η παροχή τέτοιων πληροφοριών υπόκειται στα παρακάτω:</p>
<ol style="padding-left: 40px;">
<li>την πληρωμή τέλους {ΕΙΣΑΓΕΤΕ ΤΕΛΟΣ ΑΝ ΥΠΑΡΧΕΙ}; και</li>
<li>την παροχή επαρκών στοιχείων ταυτοποίησης σας ({ΑΛΛΑΞΤΕ ΤΟ ΚΕΙΜΕΝΟ ΩΣΤΕ ΝΑ ΑΝΑΦΕΡΕΙ ΤΗΝ ΠΟΛΙΤΙΚΗ ΣΑΣ για αυτό το σκοπό, συνήθως δεχόμαστε θεωρημένη φωτοτυπία διαβατηρίου από συμβολαιογράφο και μία φωτοτυπία λογαριασμού υπηρεσίας κοινής ωφέλειας που εμφανίζει την τρέχουσα διεύθυνση σας}).</li>
</ol>
<p>Μπορούμε να παρακρατήσουμε τις προσωπικές πληροφορίες που ζητάτε, για την επιτρεπόμενη από το νόμο περίοδο.</p>
<p>Μπορείτε να μας ζητήσετε ανά πάσα στιγμή να μην επεξεργαζόμαστε τις προσωπικές σας πληροφορίες για διαφημιστικούς σκοπούς.</p>
<p>Στην πράξη, συνήθως είτε θα αποδέχεστε ρητά εκ των προτέρων την χρήση των προσωπικών σας πληροφοριών για διαφημιστικούς σκοπούς, είτε θα σας παρέχουμε τη δυνατότητα να εξαιρέσετε τις πληροφορίες σας από τη χρήση για διαφημιστικούς σκοπούς.</p>
<p><strong>K. Τρίτοι ιστότοποι</strong></p>
<p>Ο ιστότοπος μας περιλαμβάνει υπερσυνδέσμους σε, και πληροφορίες από, τρίτους ιστότοπους. Δεν μπορούμε να ελέγξουμε και δεν φέρουμε ευθύνη για τις πολιτικές προστασίας και της πρακτικές τρίτων μερών.</p>
<p><strong>L. Ενημέρωση πληροφοριών</strong></p>
<p>Παρακαλώ ενημερώστε μας αν οι πληροφορίες που έχουμε για εσάς χρειάζονται ενημέρωση ή διόρθωση.</p>
<p><strong>M. Cookies</strong></p>
<p>Ο ιστότοπος μας χρησιμοποιεί cookies. Ένα cookie είναι ένα αρχείο που περιέχει ένα αναγνωριστικό (μία σειρά γραμμάτων και αριθμών) που αποστέλλεται μέσω ενός διακομιστή και αποθηκεύεται στον περιηγητή. Το αναγνωριστικό κατόπιν αποστέλλεται πίσω στο διακομιστή κάθε φορά που ο περιηγητής ζητά μία σελίδα από το διακομιστή. Τα cookies μπορεί να είναι είτε “μόνιμα” ή cookies “σύνδεσης”: ένα μόνιμο cookie αποθηκεύεται σε έναν περιηγητή και παραμένει σε ισχύ μέχρι την ημερομηνία λήξης του, εκτός αν διαγραφεί νωρίτερα από το χρήστη, ένα cookie σύνδεσης, από την άλλη, θα λήξει με το τέλος της σύνδεσης του χρήστη, όταν κλείσει ο περιηγητής. Τα cookies συνήθως δεν περιέχουν πληροφορίες που μπορούν να ταυτοποιήσουν το χρήστη αλλά τα προσωπικά δεδομένα σας που διατηρούμε μπορεί να συνδέονται με δεδομένα που αποθηκεύονται ή ανακτώνται από cookies. {ΕΠΙΛΕΞΤΕ ΚΑΤΑΛΛΗΛΗ ΔΙΑΤΥΠΩΣΗ Χρησιμοποιούμε μόνο cookies σύνδεσης/ μόνο μόνιμα cookies / cookies σύνδεσης και μόνιμα στον ιστότοπο μας.}</p>
<ol style="padding-left: 40px;">
<li>Τα ονόματα των cookies που χρησιμοποιούμε στον ιστότοπο μας και ο σκοπός χρήσης τους ορίζεται παρακάτω:
<ol type="1">
<li>χρησιμοποιούμε στον ιστότοπο μας Google Analytics και Adwords για να αναγνωρίζουμε έναν υπολογιστή όταν ένας χρήστης {ΣΥΜΠΕΡΙΛΑΒΕΤΕ ΟΛΕΣ ΤΙΣ ΧΡΗΣΕΙΣ ΤΩΝ COOKIES ΣΤΟΝ ΙΣΤΟΤΟΠΟ ΣΑΣ επισκέπτεται τον ιστότοπο / να εντοπίσουμε τους χρήστες κατά την πλοήγηση στον ιστότοπο / να επιτρέψουμε τη χρήση καλαθιού αγορών στον ιστότοπο / να βελτιώσουμε τη λειτουργικότητα του ιστοτόπου / να αναλύσουμε τη χρήση του ιστοτόπου / να διαχειριστούμε τον ιστότοπο / να προστατευτείτε από απάτη και να βελτιώσετε την προστασία του ιστοτόπου / να προσωποποιήσουμε τον ιστότοπο για κάθε χρήστη / να στοχεύσουμε διαφημίσεις που μπορεί να ενδιαφέρουν συγκεκριμένους χρήστες / περιγράψτε τους σκοπούς};</li>
</ol>
</li>
<li>Οι περισσότεροι περιηγητές σας επιτρέπουν να μη δεχτείτε cookies—για παράδειγμα:
<ol type="1">
<li>στον Internet Explorer (version 10) μπορείτε να αποκλείσετε τα cookies χρησιμοποιώντας τις ρυθμίσεις για cookie που βρίσκονται πατώντας “Εργαλεία”, “Επιλογές Internet”, “Απόρρητο” και “Για Προχωρημένους”</li>
<li>στο Firefox (version 24) μπορείτε να αποκλείσετε όλα τα cookies πατώντας “Εργαλεία,” “Επιλογές,” “Απόρρητο,” επιλέγοντας “Χρήση προσαρμοσμένων ρυθμίσεων για το ιστορικό” από το μενού και αφαιρώντας την επιλογή “Αποδοχή cookies από ιστοτόπους”; και</li>
<li>στο Chrome (version 29), μπορείτε να αποκλείσετε όλα τα cookies αν μπείτε στο μενού “Προσαρμογή και έλεγχος” και πατώντας “Ρυθμίσεις”, “Προβολή σύνθετων ρυθμίσεων”, και “Ρυθμίσεις περιεχομένου” και επιλέγοντας “Αποκλεισμός ιστοτόπων από δεδομένα” κάτω από την επικεφαλίδα “Cookies”.</li>
</ol>
</li>
</ol>
<p>Ο αποκλεισμός όλων των cookies θα έχει αρνητική επίδραση στη λειτουργικότητα πολλών ιστοτόπων. Αν αποκλείσετε τα cookies, δεν θα μπορείτε να χρησιμοποιήσετε όλες τις λειτουργίες του ιστοτόπου σας.</p>
<ol start="3">
<li>Μπορείτε να διαγράψετε cookies ήδη αποθηκευμένα στον υπολογιστή σας—για παράδειγμα:
<ol type="1">
<li>στον Internet Explorer (version 10), θα πρέπει να διαγράψετε χειροκίνητα τα αρχεία των cookie (μπορείτε να βρείτε οδηγίες στο<a href="http://support.microsoft.com/kb/278835"> http://support.microsoft.com/kb/278835</a> );</li>
<li>στο Firefox (version 24), μπορείτε να διαγράψετε τα cookies πατώντας “Εργαλεία,” “Επιλογές,” και “Απόρρητο”, μετά επιλέγοντας “Χρήση προσαρμοσμένων ρυθμίσεων για το ιστορικό”, πατώντας “Προβολή Cookies,” και “Αφαίρεση Όλων των Cookies” και</li>
<li>στο Chrome (version 29), μπορείτε να διαγράψετε όλα τα cookies μπαίνοντας στο μενού “Προσαρμογή και έλεγχος”, και πατώντας “Ρυθμίσεις”, “Προβολή Προσαρμοσμένων ρυθμίσεων” και “Εκκαθάριση δεδομένων περιήγησης” και κατόπιν επιλέγοντας “Διαγραφή cookies και άλλων δεδομένων ιστοτόπου” προτού πατήσετε “Εκκαθάριση δεδομένων περιήγησης”.</li>
</ol>
</li>
<li>Η διαγραφή όλων των cookies θα έχει αρνητική επίδραση στη λειτουργικότητα πολλών ιστοτόπων.</li>
</ol>
<p><em>Η Website Planet δε φέρει ευθύνη και σας συμβουλεύει να συμβουλευτείτε εξειδικευμένης νομικούς αν χρησιμοποιήσετε το παραπάνω υπόδειγμα στον ιστότοπο σας.</em></p></a>';
}
function getnavbar(){
    echo '<nav class="navbar" role="navigation" aria-label="main navigation" style="background-color:rgba(44, 44, 44, 1); border-bottom: 1px solid white;">
  <div class="navbar-brand">
    <a class="navbar-item" href="https://nerdnet.geoxhonapps.com" style="color:white;">
      <img src="cdn/logo-new.png" width="30" height="50">
      
      NERDNET
    </a>


    <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="true" data-target="navbarBasicExample" >
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
      <span aria-hidden="true"></span>
    </a>
  </div>

  <div id="navbarBasicExample" class="navbar-menu" style="background-color:rgba(44, 44, 44, 1);">
    <div class="navbar-start">
      <a class="navbar-item" href="https://nerdnet.geoxhonapps.com" style="color:white;">
        Αρχική
      </a>
      <a class="navbar-item" style="color:white;">
        Documentation
      </a>

      <div class="navbar-item has-dropdown is-hoverable">
        <a class="navbar-link" style="color:white;">
          More
        </a>

        <div class="navbar-dropdown" style="background-color:rgba(44, 44, 44, 1);">
          <a class="navbar-item" style="color:white;">
            About
          </a>
          <a class="navbar-item" style="color:white;">
            Jobs
          </a>
          <a class="navbar-item" style="color:white;">
            Contact
          </a>
          <hr class="navbar-divider">
          <a class="navbar-item" style="color:white;">
            Report an issue
          </a>
        </div>
      </div>
    </div>

    <div class="navbar-end">
      <div class="navbar-item">';
      if(isset($_SESSION["loggedin"]) && $_SESSION["loggedin"] === true){
          echo '<a class="navbar-item">'.$_SESSION['username'].'/Ο λογαριασμός μου' ;
          echo'
          <div class="buttons">
          <a class="button is-danger" href="https://nerdnet.geoxhonapps.com/logout.php">
            <strong>Αποσύνδεση</strong>
          </a>';
     }else{
         echo'<div class="buttons">
          <a class="button is-primary" href="https://nerdnet.geoxhonapps.com/register.php">
            <strong>Εγγραφή</strong>
          </a>
          <a class="button is-light" href="https://nerdnet.geoxhonapps.com/login.php">
            Σύνδεση
          </a>';
     }
     echo '
        </div>
      </div>
    </div>
  </div>
 
</nav>
 
<script src="cdn/js/navbar.js"></script>';
}
function getRandomId() {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyz';
    $randomString = '';
  
    for ($i = 0; $i < 8; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }
    $randomString .='-';
    for ($i = 0; $i < 4; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }
    $randomString .='-';
    for ($i = 0; $i < 4; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }
    $randomString .='-';
    for ($i = 0; $i < 4; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }
    $randomString .='-';
    for ($i = 0; $i < 12; $i++) {
        $index = rand(0, strlen($characters) - 1);
        $randomString .= $characters[$index];
    }
    return $randomString;
}