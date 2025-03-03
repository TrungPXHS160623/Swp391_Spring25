<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Slider Lists</title>
    <style>
        body {
            background-color: #4682B4;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
        }
        .search-section {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .search-section input, .search-section select {
            padding: 5px;
            font-size: 14px;
        }
        .search-section button {
            background-color: green;
            color: white;
            padding: 8px 15px;
            border: none;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        .action-btn {
            padding: 5px 10px;
            border: none;
            cursor: pointer;
        }
        .active { background-color: yellow; }
        .deactivate { background-color: red; color: white; }
        .update { background-color: green; color: white; }
        .pagination {
            margin-top: 10px;
        }
        .pagination button {
            background-color: orange;
            padding: 5px 10px;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Slider Lists</h2>
        <div class="search-section">
            <input type="text" placeholder="Search by title">
            <button>Search by title</button>
            <select>
                <option>Slider Status</option>
            </select>
            <input type="text" placeholder="Search by link">
            <button>Search by link</button>
            <button>Add Slider</button>
        </div>
        <table>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Image</th>
                <th>BackLink</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>1</td>
                <td>Sample Title</td>
                <td>Image Placeholder</td>
                <td>www.example.com</td>
                <td class="active">Active</td>
                <td>
                    <button class="action-btn active">Active</button>
                    <button class="action-btn deactivate">Deactivate</button>
                    <button class="action-btn update">Update</button>
                </td>
            </tr>
        </table>
        <div class="pagination">
            <button><</button>
            <button>1</button>
            <button>2</button>
            <button>...</button>
            <button>></button>
        </div>
        <button style="background-color: coral; margin-top: 10px;">Customize Table</button>
    </div>
</body>
</html>