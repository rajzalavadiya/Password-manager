<?php
include 'connect.php';

$email= $_POST['email'];
$name= $_POST['name'];
$subject= $_POST['subject'];
$massage= $_POST['massage'];
$myemail="rajzalavadiya162@gmail.com";
$from= 'From: abc@gmail.com';

$sql = "INSERT INTO `contact` (`NO`, `name`, `email`, `subject`, `massage`) VALUES (NULL, '$name', '$email', '$subject', '$massage')";
mysqli_query($conn,$sql);
$response= "Your data has been submited";
echo $response;
try{
    mail($myemail,$subject,$massage,$email);
}
catch(Exception $e){}

?>