<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #4A90E2;
            text-align: center;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: center;
        }
        .search-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .search-section input, .search-section select {
            padding: 8px;
            margin-right: 10px;
        }
        .search-btn {
            background-color: #00FF00;
            color: black;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
        .pagination {
            margin-top: 20px;
        }
        .pagination button {
            padding: 10px;
            background-color: orange;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>My Orders</h2>
        <div class="search-section">
            <input type="text" placeholder="Enter Order ID" />
            <button class="search-btn">Search by Id</button>
            <select>
                <option>Order Status</option>
            </select>
            <input type="date" />
            <input type="date" />
            <button class="search-btn">Search by date</button>
        </div>
        <table>
            <tr>
                <th>Id</th>
                <th>Order Date</th>
                <th>Product Name</th>
                <th>Total Cost</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>1</td>
                <td>2024-03-03</td>
                <td>Sample Product</td>
                <td>$100</td>
                <td>Pending</td>
                <td><button class="search-btn">View Detail</button></td>
            </tr>
        </table>
        <div class="pagination">
            <button>&lt;</button>
            <button>1</button>
            <button>2</button>
            <button>...</button>
            <button>&gt;</button>
        </div>
        <button style="background-color: coral; margin-top: 10px;">Customize Table</button>
    </div>
</body>
</html>