<?php
include 'connect.php';

$email= $_POST['email'];
$pass= $_POST['password'];
$sql = "select * from user where email = '$email' AND password = '$pass'";
$result= mysqli_query($conn,$sql);
$count=mysqli_num_rows($result);
$response= array();
if($count>0){
    $response="exist";
}
else{
    $response="";
}
echo $response;
?>