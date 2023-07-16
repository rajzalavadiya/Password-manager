<?php
    include 'connect.php';
    $email= $_POST['email'];
    $title= $_POST['title'];
    $date=$_POST['date'];
    $user= $_POST['user'];
    $password= $_POST['password'];

    $sql = "select ID from user WHERE email = '$email'";
    $result= mysqli_query($conn,$sql);
    $row=  mysqli_fetch_assoc($result);

    $id=$row["ID"];

    $sql1="INSERT INTO `password` (`ID`,`NO`,`title`, `date`, `user`, `password`) VALUES ('$id',NULL,'$title', '$date', '$user', '$password')";
  

    if(mysqli_query($conn,$sql1)){
        $response="Submitted";
        
    }
    else{
        $response="";
    }

    echo $response;



?>