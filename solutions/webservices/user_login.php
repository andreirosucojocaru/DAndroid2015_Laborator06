<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$username = $_POST['username'];
	$password = $_POST['password'];
	  
	if(isset($username) && isset($password)) {
		$query = "SELECT u.id AS user_id, u.username AS username, u.email AS email, u.password AS password FROM user u WHERE u.username='".$username."' AND u.password='".$password."'";
		$result = mysqli_query($connection, $query);
		if (mysqli_num_rows($result) > 0) {
			$record = mysqli_fetch_assoc($result);
			$message = array(
				"user_id" => $record["user_id"],
				"email" => $record["email"],
				"result" => "success"
			);
		} else {
			$message = array(
			   "user_id" => "-1",
			   "email" => "null",
			   "result" => "failure"
			);
		}
		mysqli_close($connection);
	} else {
		$message = array(
			"user_id" => "-1",
			"email" => "null",
			"result" => "failure"
		);
	}
	echo json_encode($message);
?>