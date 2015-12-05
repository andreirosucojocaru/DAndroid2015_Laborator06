<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$user_id = $_POST['user_id'];
	$email = $_POST['email'];
	$password = $_POST['password'];
	  
	if(isset($user_id) && isset($email) && isset($password)) {
		$query = "UPDATE user u SET u.email='".$email."', u.password='".$password."' WHERE u.id=".$user_id."";
		$result = mysqli_query($connection, $query);
		if ($result) {
			$message = array(
				"result" => "success"
			);
		} else {
			$message = array(
			   "result" => "failure"
			);
		}
		mysqli_close($connection);
	} else {
		$message = array(
			"result" => "failure"
		);
	}
	echo json_encode($message);
?>