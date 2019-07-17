<?php
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    $image = $_POST['image'];
    $name = $_POST['name'];
 
    require_once('dbDetails.php');
 
    $sql ="SELECT id FROM images ORDER BY id ASC";
 
    $res = mysqli_query($con,$sql);
 
    $id = 0;
 
    while($row = mysqli_fetch_array($res)){
        $id = $row['id'];
    }
 
    $path = "uploads/$id.png";
 
    $actualpath = "http://000.000.00.00/UploadImage/$path";  //enter your server ip in 000.000.00.00
 
    $sql = "INSERT INTO images (url,name) VALUES ('$actualpath','$name')";
 
    if(mysqli_query($con,$sql)){
        file_put_contents($path,base64_decode($image));
        $last_line = exec("python firts.py $id");
        echo $last_line;
    }
 
    mysqli_close($con);
}
else{
    echo "Error";
}
