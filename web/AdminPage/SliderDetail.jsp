<!DOCTYPE html>
<html>
<head>
    <title>Slider Detail</title>
    <style>
        body {
            background-color: #337ab7;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: auto;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px gray;
        }
        .title {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .field {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid black;
            border-radius: 5px;
        }
        .image-box {
            width: 100%;
            height: 150px;
            background-color: #337ab7;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 18px;
        }
        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .button {
            width: 48%;
            padding: 10px;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        .update-button {
            background-color: #28a745;
        }
        .back-button {
            background-color: #17a2b8;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="title">Slider Detail</div>
        <input type="text" class="field" placeholder="Slider Id" disabled>
        <div class="image-box">Image</div>
        <input type="text" class="field" placeholder="Slider Title">
        <input type="text" class="field" placeholder="Back Link">
        <input type="text" class="field" placeholder="Status">
        <input type="text" class="field" placeholder="Notes">
        <input type="text" class="field" placeholder="Created Date" disabled>
        <input type="text" class="field" placeholder="Update Date" disabled>
        <input type="text" class="field" placeholder="Last Updated Date" disabled>
        <input type="text" class="field" placeholder="Last Updated By" disabled>
        <div class="button-container">
            <button class="button update-button">Update</button>
            <button class="button back-button">Back to slider list</button>
        </div>
    </div>
</body>
</html>
