<?php
    include 'connect.php';
    if(isset($_POST['email']) && isset($_POST['otp'])){
    $email= $_POST['email'];
    $subject='Your Verification Code';
    $otp= 'Your Verification Code:- ' . $_POST['otp'];
    $from= 'From: abc@gmail.com';
    $sql = "select * from user where email = '$email'";
    $result= mysqli_query($conn,$sql);
    $count=mysqli_num_rows($result);

    if($count>0){
        mail($email,$subject,$otp,$from);
        $response="otp send has been sucessfully";
    }
    else{
        $response="Email id not exist";
    }  
    echo $response;
    }

if(isset($_POST['email']) && isset($_POST['password'])){

    $email= $_POST['email'];
    $pass= $_POST['password'];
    $sql = "select * from user where email = '$email'";
    $result= mysqli_query($conn,$sql);
    $count=mysqli_num_rows($result);
    $response= array();
    if($count>0){
        $sql1="UPDATE `user` SET `password` = '$pass' WHERE email = '$email'";
        mysqli_query($conn,$sql1);
        $response="Password has been changed";
    }
    else{
        $response="";
    }
    echo $response;
}
?>