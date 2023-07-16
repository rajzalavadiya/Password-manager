<?php
    include 'connect.php';

    $title= $_POST['title'];
    $date=$_POST['date'];
    $user= $_POST['user'];
    $password= $_POST['password'];
    $NO=$_POST['NO'];

    $sql="UPDATE `password` SET title='$title', date='$date',user='$user',password='$password' WHERE NO ='$NO'";

    if(mysqli_query($conn,$sql)){
        $response="Updated";
    }
    else{
        $response="";
    }

    echo $response;


?>