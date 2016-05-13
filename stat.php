<?php
$servername = "localhost";
$username = "root";
$password = "ramax391";
$dbname = "test";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn-> connect_error){
	die("connection failed: " . $conn->connect_error);

}
$d1 = date("Y")."-".date("m")."-".(date("d"));
$d2 = date("Y")."-".date("m")."-".(date("d")-1);

$sql = "SELECT count(*) FROM time WHERE onTouch=1 and mydate="."'".$d1."'";
$sql2 = "SELECT count(*) FROM time WHERE onTouch=1 and mydate="."'".$d2."'";

$xmlcode ="<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";
$result = $conn->query($sql);

if($result->num_rows > 0 ) {

	while($row= $result->fetch_assoc()) {
	//echo "date : ".$row["count(*)"] ;

	$t1 = $row["count(*)"];
	$xmlcode.="<d1>$d1</d1>\n";
	$xmlcode.="<t1>$t1</t1>\n";
	}
}
else{
echo "0 results";
}

$result2 = $conn->query($sql2);
if($result2->num_rows > 0 ) {

	while($row2= $result2->fetch_assoc()) {
	//echo "date : ".$row["count(*)"] ;

	$t2 = $row2["count(*)"];
	$xmlcode.="<d2>$d2</d2>\n";
	$xmlcode.="<t2>$t2</t2>\n";
	}
}
else{
echo "0 results";
}

$filename = "./stat.xml";

file_put_contents($filename, $xmlcode);

$conn->close();

?>
