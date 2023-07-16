<?php

include 'connect.php';

$email= $_POST['email'];
$oldPass=$_POST['oldPassword'];
$pass= $_POST['password'];
$sql = "select * from user where email = '$email' AND password = '$oldPass'";
$result= mysqli_query($conn,$sql);
$count=mysqli_num_rows($result);
if($count>0){
    $sql1="UPDATE `user` SET `password` = '$pass' WHERE email = '$email'";
    mysqli_query($conn,$sql1);
    $response="Password has been changed";
}
else{
    $response="";
}
echo $response;
?>