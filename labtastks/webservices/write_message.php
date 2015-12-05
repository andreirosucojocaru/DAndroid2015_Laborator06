<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$sender_id = $_POST['sender_id'];
	$recipient_id = $_POST['recipient_id'];
	$subject = $_POST['subject'];
	$content = $_POST['content'];
	  
	if(isset($sender_id) && isset($recipient_id) && isset($subject) && isset($content)) {
		$query = "INSERT INTO message (sender_id, recipient_id, subject, content) VALUES ('".$sender_id."', '".$recipient_id."', '".$subject."', '".$content."')";
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