<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$message_id = $_POST['message_id'];
	  
	if(isset($message_id)) {
		$query = "UPDATE message m SET m.status = 'Y' WHERE m.id=".$message_id;
		mysqli_query($connection, $query);
		$query = "SELECT u1.username AS sender, u2.username AS recipient, m.subject AS subject, m.content AS content, m.timestamp AS timestamp FROM message m, user u1, user u2 WHERE m.id=".$message_id." AND u1.id = m.sender_id and u2.id = m.recipient_id";
		$result = mysqli_query($connection, $query);
		if (mysqli_num_rows($result) > 0) {
			$record = mysqli_fetch_assoc($result);
			$message = array(
				"sender" => $record["sender"],
				"recipient" => $record["recipient"],
				"subject" => $record["subject"],
				"content" => $record["content"],
				"timestamp" => $record["timestamp"]
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