<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$username = $_POST['username'];
	  
	if(isset($username)) {
		$query = "SELECT u.id AS user_id, u.username AS username FROM user u WHERE u.username<>'".$username."'";
		$result = mysqli_query($connection, $query);
		if (mysqli_num_rows($result) > 0) {
			$message = array();
			while ($record = mysqli_fetch_assoc($result)) {
				$user = array(
					"user_id" => $record["user_id"],
					"username" => $record["username"]
				);
				$message[] = $user;
			}
		} else {
			$message = array();
		}
		mysqli_close($connection);
	} else {
		$message = array();
	}
	echo json_encode($message);
?>