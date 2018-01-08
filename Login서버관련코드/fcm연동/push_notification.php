<?php

	function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key =' . "AAAAPmAkG2A:APA91bG8MSgTSofcVgIvJ5EfkEi-BIEWfkADgAv1FMbxdwKM3kXQirpnuIiLs9ctzA_5G8ymzH8EmFJsuIL18YW5ncSbkLc_2yT4zaEqigS4J5gAETqbyBksc25eAO_BMUrwdiucPLs5"
,
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}


	//데이터베이스에 접속해서 토큰들을 가져와서 FCM에 발신요청

	$conn = mysqli_connect("localhost","syoung1394","donghang123","syoung1394");
// 나중에 요부분을 통해서 전달할 수 있을 것같다. 원하는 기기에
	$sql = "Select userToken From HELPER";

	$result = mysqli_query($conn,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["userToken"];
		}
	}

	mysqli_close($conn);

        $myMessage = $_POST['message']; //폼에서 입력한 메세지를 받음
	if ($myMessage == ""){
		$myMessage = "새글이 등록되었습니다.";
	}

	$message = array("message" => $myMessage);
	$message_status = send_notification($tokens, $message);
	echo $message_status;

 ?>
