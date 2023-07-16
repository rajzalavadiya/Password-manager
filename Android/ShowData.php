<?php
include 'connect.php';
$email= $_POST['email'];
$sql = "select ID from user WHERE email = '$email'";
$result= mysqli_query($conn,$sql);
$row=  mysqli_fetch_assoc($result);
$id=$row["ID"];
$response=array();

$sql1 = "select * from password where ID='$id'";
$result1= mysqli_query($conn,$sql1);

while ($line = mysqli_fetch_array($result1)) {
    array_push($response,$line);
} 
echo json_encode($response);

?>