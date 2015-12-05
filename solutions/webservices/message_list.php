<?php
	$connection = mysqli_connect("localhost", "rand0956_android", "dandroid2015", "rand0956_dandroid2015");
	if (!$connection) {
		$error="Connection to Database Failed";
	}
	if (isset($error)) {
		die($error);
	}
	  
	$user_id = $_POST['user_id'];
	  
	if(isset($user_id)) {
		$query = "SELECT m.id AS message_id, u1.username AS sender, u2.username AS recipient, m.subject AS subject, m.content AS content, m.status AS status FROM message m, user u1, user u2 WHERE m.recipient_id=".$user_id." AND u1.id = m.sender_id and u2.id = m.recipient_id ORDER BY m.timestamp DESC";
		$result = mysqli_query($connection, $query);
		if (mysqli_num_rows($result) > 0) {
			$message_list = array();
			while ($record = mysqli_fetch_assoc($result)) {
				$message = array(
					"message_id" => $record["message_id"],
					"sender" => $record["sender"],
					"recipient" => $record["recipient"],
					"subject" => $record["subject"],
					"content" => $record["content"],
					"status" => $record["status"]
				);
				$message_list[] = $message;
			}
		} else {
			$message_list = array();
		}
		mysqli_close($connection);
	} else {
		$message_list = array();
	}
	echo json_encode($message_list);
?>