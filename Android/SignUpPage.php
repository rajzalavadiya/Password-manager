<?php

include 'connect.php';
$name =$_POST['name'];
$email= $_POST['email'];
$pass= $_POST['password'];
$sql = "select * from user where email = '$email'";
$result= mysqli_query($conn,$sql);
$count=mysqli_num_rows($result);
$response= array();
if($count>0){
    $response="";
}
else{
    $sql2="INSERT INTO `user` (`ID` ,`name`, `email`, `password`) VALUES (NULL,'$name', '$email', '$pass');";
    $result2= mysqli_query($conn,$sql2);
    $response="Sign up has been sucessfully";
}

echo $response;
?>