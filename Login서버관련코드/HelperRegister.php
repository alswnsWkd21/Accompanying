<?php
$con=mysqli_connect("localhost","syoung1394","donghang123","syoung1394");

$userID = $_POST["userID"];
$userPassword = $_POST["userPassword"];
$userName = $_POST["userName"];
$userPhone = $_POST["userPhone"];
$userToken = $_POST["userToken"];

$statement = mysqli_prepare($con, "INSERT INTO HELPER VALUES(?,?,?,?,?)");
mysqli_stmt_bind_param($statement,"ssss", $userID, $userPassword, $userName, $userPhone, $userToken);
mysqli_stmt_execute($statement);

$response=array();
$response["success"]=true;

echo json_encode($response);
?>
