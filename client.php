<?php
$servername = "103.84.159.270";
$username = "*******";
$password = "*******";
$dbname = "ChatServer";

try {
    $conn = new PDO("mysql:host=$servername;port=3306;dbname=$dbname", $username, $password);

    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

     // sql to create table
    $sql0 = "CREATE TABLE client (
                client_id INT AUTO_INCREMENT NOT NULL,
                username VARCHAR(100) NOT NULL,
                email VARCHAR(100),
                password VARCHAR(100),
                PRIMARY KEY (client_id)
				)";

	$sql1 = "CREATE TABLE chat_history (
                chat_id INT AUTO_INCREMENT NOT NULL,
				client_id INT NOT NULL,
                chat VARCHAR(21500) NOT NULL,
                time_stamp VARCHAR(100) NOT NULL, 
                PRIMARY KEY (chat_id),
                FOREIGN KEY (client_id) REFERENCES client(client_id)
				)";

//electricity
//timestamp: HH:MM:SS:DD:MM:YY

	$sql2 = "INSERT INTO client(client_id, username, email, password) VALUES ('1', 'puhi', 'shuhan.mirza@gmail.com', 'shuhan1234')";
	$sql3 = "INSERT INTO client(client_id, username, email, password) VALUES ('2', 'meow', 'tanjan.sj@gmail.com', 'meowmeow')";
	$sql4 = "INSERT INTO client(client_id, username, email, password) VALUES ('3', 'rb101', 'robotbakers101@gmail.com', 'pupu')";
	$sql5 = "INSERT INTO chat_history(chat_id,client_id, chat, time_stamp) VALUES ('1','1', 'puchipuchimeow puhi? tataa12 34567', '02:25:59:18:11:2017')";

    $conn->exec($sql0);
    echo "sql0";
    $conn->exec($sql1);
    echo "sql1";
    $conn->exec($sql2);
    echo "sql2";
    $conn->exec($sql3);
    echo "sql3";
    $conn->exec($sql4);
    echo "sql4";
    $conn->exec($sql5);
    echo "sql5";

    }
catch(PDOException $e)
    {
    echo $e->getMessage();
    }

$conn = null;
?>




