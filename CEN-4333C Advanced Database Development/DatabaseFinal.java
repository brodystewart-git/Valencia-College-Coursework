
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class DatabaseFinal {

    // ---- UPDATE THESE TO MATCH YOUR MYSQL SETUP ----
    private static final String URL = "jdbc:mysql://localhost:3306/photo_management";
    private static final String USER = "root";
    private static final String PASSWORD = "PASSWORDHERE";
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to database!");
            runTest(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private static void runTest(Connection conn) throws SQLException {
            // 1. Create a user
            User user = new User(0, "Brody", "Stewart");
            int userId = userCRUD.createUser(conn, user);
            System.out.println("Created User with ID: " + userId);

            // 3. Create a photo assigned to that user
            Photo photo = new Photo(
                    userId,
                    3, // Genre 3 is FOOD
                    "Ugly Cheeseburger",
                    "/images/chzbrg.jpg",
                    Date.valueOf("2025-11-30")
            );
            int photoId = photosCRUD.createPhoto(conn, photo);
            System.out.println("Created Photo with ID: " + photoId);

            // 4. Read the photo back from DB
            Photo dbPhoto = photosCRUD.getPhotoById(conn, photoId);
            System.out.println("\nPhoto after CREATE:");
            photosCRUD.printPhoto(dbPhoto, conn);

            // 5. Update the photo
            dbPhoto.setTitle("RADICAL BURGER!!!! (Edited)");
            dbPhoto.setPath("/images/chzbrg_edited.jpg");
            photosCRUD.updatePhoto(conn, dbPhoto);

            // 6. Read again after update
            Photo updatedPhoto = photosCRUD.getPhotoById(conn, photoId);
            System.out.println("\nPhoto after UPDATE:");
            photosCRUD.printPhoto(updatedPhoto, conn);

            // 7. Delete the photo
            photosCRUD.deletePhoto(conn, photoId);
            System.out.println("\nPhoto with ID " + photoId + " deleted.");

            // 8. Try to read it again to confirm deletion
            Photo deletedCheck = photosCRUD.getPhotoById(conn, photoId);
            if (deletedCheck == null) {
                System.out.println("Confirmed: photo no longer exists in the database.");
            } else {
                System.out.println("Something went wrong, photo still exists:");
                photosCRUD.printPhoto(deletedCheck, conn);
            }
        }
}

class Photo {
    private int photoId; // Corresponds to 'photo_id'
    private int userId; // Corresponds to 'user_id'
    private int genreId; // Corresponds to 'genre_id'
    private String title; // Corresponds to 'title'
    private String filepath; //Corresponds to 'file_path'
    private Date dateTaken; // Corresponds to 'date_taken'
    
    public Photo() {};
    
    public Photo(int userId, int genreId, String title, String filepath, Date dateTaken) {
        this.userId = userId;
        this.genreId = genreId;
        this.title = title;
        this.filepath = filepath;
        this.dateTaken = dateTaken;
    }
    
    public int getPhotoId() { return photoId; };
    public void setPhotoId(int photoId) { this.photoId = photoId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getPath() { return filepath; }
    public void setPath(String filepath) { this.filepath = filepath; }
    
    public Date getDateTaken() { return dateTaken; }
    public void setDateTaken(Date dateTaken) { this.dateTaken = dateTaken; }
}

class User {
    private int userId; // Corresponds to 'user_id'
    private String firstName; // Corresponds to 'first_name'
    private String lastName; // Corresponds to 'last_name'

    public User() {}
    
    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
}

class Genre {
    private int genreId; // Corresponds to 'genre_id'
    private String genreName; // Corresponds to 'genre_name'

    public Genre() {}
    
    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    public int getGenreId() { return genreId; }
    public String getGenreName() { return genreName; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
    public void setGenreName(String genreName) { this.genreName = genreName; }
}

/* EVERYTHING BELOW IS AI ASSISTED
 * I had genuinely no idea how to go about this as I've only every done something with MYSQL and Java a single time.
 * That single time was incredibly simple too, as it was just a single table and barely any changes.
 * So for my project, I had AI assist me on the actual connection (CRUD) portion of this system. 
 * I was still largely aware of what it was doing and learned a lot as well, I just had no clue where to start.
 */

class userCRUD {
	public static int createUser(Connection conn, User user) throws SQLException {

	    // 1. Check if user already exists
	    String checkSql = "SELECT user_id FROM users WHERE first_name = ? AND last_name = ?";
	    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
	        checkStmt.setString(1, user.getFirstName());
	        checkStmt.setString(2, user.getLastName());
	        
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next()) {
	                int existingId = rs.getInt("user_id");
	                System.out.println("User already exists with ID: " + existingId);
	                user.setUserId(existingId);
	                return existingId;
	            }
	        }
	    }

	    // 2. Insert new user if none found
	    String sql = "INSERT INTO users (first_name, last_name) VALUES (?, ?)";
	    try (PreparedStatement stmt =
	                 conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, user.getFirstName());
	        stmt.setString(2, user.getLastName());
	        stmt.executeUpdate();

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                user.setUserId(id);
	                return id;
	            }
	        }
	    }

	    return -1;
	}

	    public static User getUserById(Connection conn, int userId) throws SQLException {
	        String sql = "SELECT user_id, first_name, last_name FROM users WHERE user_id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, userId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    User u = new User();
	                    u.setUserId(rs.getInt("user_id"));
	                    u.setFirstName(rs.getString("first_name"));
	                    u.setLastName(rs.getString("last_name"));
	                    return u;
	                }
	            }
	        }
	        return null;
	    }

	    public static void updateUser(Connection conn, User user) throws SQLException {
	        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE user_id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, user.getFirstName());
	            stmt.setString(2, user.getLastName());
	            stmt.setInt(3, user.getUserId());
	            stmt.executeUpdate();
	        }
	    }

	    public static void deleteUser(Connection conn, int userId) throws SQLException {
	        String sql = "DELETE FROM users WHERE user_id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, userId);
	            stmt.executeUpdate();
	        }
	    }
}

class genreCRUD {
    public static Genre getGenreById(Connection conn, int genreId) throws SQLException {
        String sql = "SELECT genre_id, genre_name FROM genre WHERE genre_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, genreId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genre g = new Genre();
                    g.setGenreId(rs.getInt("genre_id"));
                    g.setGenreName(rs.getString("genre_name"));
                    return g;
                }
            }
        }
        return null;
    }
}

class photosCRUD {

    public static int createPhoto(Connection conn, Photo photo) throws SQLException {
        String sql = "INSERT INTO photos (user_id, genre_id, title, file_path, date_taken) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, photo.getUserId());
            stmt.setInt(2, photo.getGenreId());
            stmt.setString(3, photo.getTitle());
            stmt.setString(4, photo.getPath());
            stmt.setDate(5, photo.getDateTaken());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    photo.setPhotoId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public static Photo getPhotoById(Connection conn, int photoId) throws SQLException {
        String sql = "SELECT photo_id, user_id, genre_id, title, file_path, date_taken " +
                     "FROM photos WHERE photo_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, photoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Photo p = new Photo();
                    p.setPhotoId(rs.getInt("photo_id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setGenreId(rs.getInt("genre_id"));
                    p.setTitle(rs.getString("title"));
                    p.setPath(rs.getString("file_path"));
                    p.setDateTaken(rs.getDate("date_taken"));
                    return p;
                }
            }
        }
        return null;
    }

    public static void updatePhoto(Connection conn, Photo photo) throws SQLException {
        String sql = "UPDATE photos SET user_id = ?, genre_id = ?, title = ?, " +
                     "file_path = ?, date_taken = ? WHERE photo_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, photo.getUserId());
            stmt.setInt(2, photo.getGenreId());
            stmt.setString(3, photo.getTitle());
            stmt.setString(4, photo.getPath());
            stmt.setDate(5, photo.getDateTaken());
            stmt.setInt(6, photo.getPhotoId());
            stmt.executeUpdate();
        }
    }

    public static void deletePhoto(Connection conn, int photoId) throws SQLException {
        String sql = "DELETE FROM photos WHERE photo_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, photoId);
            stmt.executeUpdate();
        }
    }

    // Helper for printing
    public static void printPhoto(Photo p, Connection conn) {
        if (p == null) {
            System.out.println("Photo is null");
            return;
        }

        try {
            Genre g = genreCRUD.getGenreById(conn, p.getGenreId());
            String genreName = (g != null ? g.getGenreName() : "Unknown");

            System.out.println("Photo ID: " + p.getPhotoId());
            System.out.println("User ID: " + p.getUserId());
            System.out.println("Genre: " + genreName);
            System.out.println("Title: " + p.getTitle());
            System.out.println("Path: " + p.getPath());
            System.out.println("Date Taken: " + p.getDateTaken());
        } catch (SQLException e) {
            System.out.println("Error loading genre name: " + e.getMessage());
        }
    }
}
