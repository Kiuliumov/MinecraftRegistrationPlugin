
# Registration Plugin for Minecraft

This plugin allows you to add a registration and login system to your Minecraft server. Players must register with a password upon joining, and log in with their credentials every time they reconnect. The plugin also restricts movement and actions until the player successfully logs in.

## Features

- **Player Registration**: Players can register their account with a username and password.
- **Login System**: Players can log in with their credentials each time they join the server.
- **Security**: Passwords are stored securely, and only registered players can log in.
- **Movement Restrictions**: Players who are not logged in are restricted from moving and taking damage while in Spectator Mode.
- **Database Support**: Stores player information in a SQLite database to persist across server restarts.
- **Game Mode Restrictions**: Non-logged-in players are kept in **Spectator Mode** until they log in or register.

## Requirements

- **Minecraft Version**: Paper 1.16 or higher
- **Java Version**: 8 or higher
- **Database**: SQLite (included in the plugin)

## Installation

1. Download the latest release from the [Releases Page](https://github.com/yourusername/registration-plugin/releases).
2. Place the `.jar` file into the `plugins/` folder of your Minecraft server.
3. Start the server, and the plugin will automatically create the necessary SQLite database (`players.db`) to store player data.
4. Customize the `config.yml` (if applicable) for any settings or preferences.

## Usage

### Registration

1. When a player joins the server for the first time, they are prompted to register by using the `/register` command. They must provide a password and repeat it to confirm.
2. The registration command requires two arguments: `password` and `repeat password`.

Example:
```
/register myPassword myPassword
```

### Login

1. After registration, players must log in each time they reconnect using the `/login` command with their password.
2. The login command requires one argument: `password`.

Example:
```
/login myPassword
```

If the password is correct, the player is logged in and can move and take damage normally.

### Restrictions

- Players who are not logged in will be kept in **Spectator Mode** and cannot move or take damage.
- Players are only allowed to move and take damage once they log in successfully.

### Commands

- `/register <password> <repeat password>` - Register a new account.
- `/login <password>` - Log in to an existing account.
- `/register help` - Displays usage instructions for the `/register` command.

### Permissions

This plugin does not require additional permissions for basic functionality, but you can manage access to commands through the permissions plugin of your choice (e.g., [PermissionsEx](https://www.spigotmc.org/resources/permissionsex.202/), [LuckPerms](https://www.spigotmc.org/resources/luckperms.58774/)).

## Configuration
!(assets/screenshots/register.png)
!(assets/screenshots/login.png)
!(assets/screenshots/login-success.png)
!(assets/screenshots/register-success.png)


- The plugin uses a simple SQLite database to store player information.
- No external configuration is required out of the box, but you may modify the code or extend it to include a configuration file if desired.

## Code Structure

### Main Classes

- **Database.java**: Manages the SQLite database connection and queries to store and retrieve player data.
- **LoginCommand.java**: Handles the login functionality, checking if the player is registered and if the password is correct.
- **RegisterCommand.java**: Handles the registration process, ensuring the player's password is valid and confirming the registration.
- **PlayerJoinListener.java**: Listens for player join events to handle whether the player needs to register or log in.
- **PlayerMovementListener.java**: Prevents movement and damage for players who are not logged in.

### Database Schema

The plugin uses a simple SQLite database with the following table structure:

```sql
CREATE TABLE IF NOT EXISTS players (
    username TEXT PRIMARY KEY,
    password TEXT,
    ip TEXT,
    lastLogin INTEGER
);
```

## Troubleshooting

- **Problem**: Player cannot register or log in.
  - **Solution**: Ensure the database connection is properly established and that there is no conflicting data in the SQLite database (`players.db`).
  
- **Problem**: Player can move even though they haven’t logged in.
  - **Solution**: Verify that the listener is properly set up and that the plugin is correctly intercepting events to restrict movement and damage.

## License

This plugin is released under the MIT License.


## BE CAUTIOUS TAHT I ONLY MADE THIS PLUGIN FOR FUN. It's fine for use for smaller scale servers but it uses an SQLite database which cannot handle large amounts of data well, as well as is vunarable to different hacker attacks.


## Screenshots 
![](screenshots/register.png)

![](screenshots/login.png)

![](screenshots/login-success.png)

![](screenshots/register-success.png)
