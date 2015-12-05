<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$username = $_POST['username'];
	$email = $_POST['email'];
	$password = $_POST['password'];
	  
	if(isset($username) && isset($email) && isset($password)) {
		$query = "INSERT INTO user (username, email, password) VALUES ('".$username."', '".$email."', '".$password."')";
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