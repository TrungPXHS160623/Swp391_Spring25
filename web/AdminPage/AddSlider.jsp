<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Slider</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #4682B4;
        }
        .form-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h3 class="text-center">Add New Slider</h3>
            <form action="${pageContext.request.contextPath}/sliderlistcontroller" method="post">
                <input type="hidden" name="action" value="add">
                <div class="mb-3">
                    <label class="form-label">Title:</label>
                    <input type="text" class="form-control" name="title" placeholder="Enter slider title" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Image URL:</label>
                    <input type="text" class="form-control" name="imageUrl" placeholder="Paste image link" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Backlink:</label>
                    <input type="text" class="form-control" name="backlink" placeholder="Enter backlink URL">
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select class="form-select" name="status">
                        <option value="1">Active</option>
                        <option value="0">Inactive</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Notes:</label>
                    <textarea class="form-control" name="notes" rows="3" placeholder="Add any notes..."></textarea>
                </div>
                <button type="submit" class="btn btn-success w-100">Add Slider</button>
            </form>
        </div>
    </div>
</body>
</html>