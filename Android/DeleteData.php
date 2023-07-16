<?php
    include 'connect.php';
    $NO=$_POST['NO'];

    $sql="DELETE FROM `password` WHERE NO ='$NO'";

    if(mysqli_query($conn,$sql)){
        $response="Deleted";
    }
    else{
        $response="";
    }

    echo $response;


?>