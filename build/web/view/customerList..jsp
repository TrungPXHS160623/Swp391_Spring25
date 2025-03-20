<!DOCTYPE html>
<html>
    <head>
        <title>Customer Detail</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h2>Customer Detail</h2>
            <form>
                <div class="mb-3">
                    <label class="form-label">Full Name:</label>
                    <input type="text" class="form-control" placeholder="Enter Full Name">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" class="form-control" placeholder="Enter Email">
                </div>
                <div class="mb-3">
                    <label class="form-label">Gender:</label>
                    <select class="form-select">
                        <option value="1">Male</option>
                        <option value="0">Female</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone Number:</label>
                    <input type="text" class="form-control" placeholder="Enter Phone Number">
                </div>
                <div class="mb-3">
                    <label class="form-label">Address:</label>
                    <input type="text" class="form-control" placeholder="Enter Address">
                </div>
                <div class="mb-3">
                    <label class="form-label">Role:</label>
                    <select class="form-select">
                        <option value="2">Customer</option>
                        <option value="1">Admin</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Status:</label>
                    <select class="form-select">
                        <option value="1">Active</option>
                        <option value="0">Inactive</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Updated By:</label>
                    <input type="text" class="form-control" placeholder="Enter Updated By">
                </div>
                <div class="mb-3">
                    <label class="form-label">Updated Date:</label>
                    <input type="date" class="form-control">
                </div>
                <button type="submit" class="btn btn-warning">Edit</button>
                <button type="submit" class="btn btn-success">Save</button>
                <button type="reset" class="btn btn-secondary">Cancel</button>
            </form>
        </div>
    </body>
</html>
