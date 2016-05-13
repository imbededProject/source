<?php
$seconds = 10;
sleep($seconds);

//$fp = fopen("/sys/class/gpio/gpio16/value",'r');
$fp = fopen("in.txt",'r');

if(!$fp)
{
	echo 'fail';
	exit;
}


//$touch = fread(fp,1);
//$touch = 1;
//echo $touch;
while(!feof($fp))
{
	$char = fgetc($fp);
	if( $char == '1')
	{
		system("sudo ./vibon");
	}
	if(!feof($fp)){
	echo ($char == "\n" ? "<br />" : $char);
	}

}
?>
