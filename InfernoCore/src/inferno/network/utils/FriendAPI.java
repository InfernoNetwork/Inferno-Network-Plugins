package inferno.network.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import inferno.network.Main;

public class FriendAPI {

	static LinkedList<Person> people = new LinkedList<Person>();

	static LinkedList<FriendRequest> requests = new LinkedList<FriendRequest>();

	public static void init() {
		loadFriends();
	}

	private FriendAPI() {
	}

	static public LinkedList<UUID> getFriends(UUID player) {
		Person p = get(player);
		if (p == null)
			return new LinkedList<UUID>();
		return p.friends;
	}

	static public void acceptRequest(FriendRequest r) {
		friend(r.confirmer, r.sender);
		requests.remove(r);
	}

	static public void denyRequest(FriendRequest r) {
		requests.remove(r);
	}

	static public void addRequest(UUID sender, UUID confirmer) {
		for (FriendRequest f : requests)
			if (f.sender.equals(sender) && f.confirmer.equals(confirmer))
				return; // already
						// added
			else if (f.confirmer.equals(confirmer)) {
				// You can only have 1 pending friend request
				requests.remove(f);
				break;
			}
		requests.add(new FriendRequest(sender, confirmer));
	}

	static public void friend(UUID p1, UUID p2) {
		getAdd(p1).friends.add(p2);
		getAdd(p2).friends.add(p1);
		updateDBAdd(p1, p2);
	}

	static public boolean isFriends(UUID p1, UUID p2) {
		for (Person p : people)
			if (p.uuid.equals(p1))
				for (UUID f : p.friends)
					if (f.equals(p2))
						return true;
		return false;
	}

	static public Person get(UUID person) {
		for (Person p : people)
			if (p.uuid.equals(person))
				return p;
		return null;
	}

	static public Person getAdd(UUID person) {
		Person p = get(person);
		if (p == null) {
			p = new Person(person);
			people.add(p);
		}
		return p;

	}

	static public FriendRequest getRequest(UUID confirmer) {
		for (FriendRequest r : requests)
			if (confirmer.equals(r.confirmer))
				return r;
		return null;
	}

	static public void unfriend(UUID p1, UUID p2) {
		Person person1 = get(p1);
		if (person1 != null)
			person1.friends.remove(p2);
		Person person2 = get(p2);
		if (person2 != null)
			person2.friends.remove(p1);
		updateDBRemove(p1, p2);
	}

	static public class FriendRequest {
		UUID confirmer;
		UUID sender;

		public FriendRequest(UUID sender, UUID confirmer) {
			this.sender = sender;
			this.confirmer = confirmer;
		}

		public UUID getConfirmer() {
			return confirmer;
		}

		public UUID getSender() {
			return sender;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (!(o instanceof FriendRequest))
				return false;
			FriendRequest other = (FriendRequest) o;
			return other.sender.equals(this.sender) && other.confirmer.equals(this.confirmer);
		}
	}

	static public class Person {
		private LinkedList<UUID> friends;
		private UUID uuid;
		/**
		 * if true then requests to add this person as a friend should not be
		 * allowed
		 */
		private boolean requestsDisabled;

		public Person(UUID name) {
			this.uuid = name;
			friends = new LinkedList<UUID>();
		}

		public void setRequestsDisabled(boolean disabled) {
			if (disabled == requestsDisabled)
				return;
			requestsDisabled = disabled;
			updateDB(this, requestsDisabled);
		}

		public boolean isRequestsDisabled() {
			return requestsDisabled;
		}

		public UUID[] getFriends() {
			return friends.toArray(new UUID[friends.size()]);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o instanceof Person)
				return ((Person) o).uuid.equals(this.uuid);
			return false;
		}

		public static class Value {
			public String sVal;
			public int iVal;
			public boolean bVal;
			public double dVal;
			public DATA_TYPE type;

			public static enum DATA_TYPE {
				STRING, INTEGER, DOUBLE, BOOLEAN
			};

			private Value() {
			}

			public Value(String val) {
				type = DATA_TYPE.STRING;
				this.sVal = val;
			}

			public Value(int val) {
				type = DATA_TYPE.INTEGER;
				this.iVal = val;
			}

			public Value(double val) {
				type = DATA_TYPE.DOUBLE;
				this.dVal = val;
			}

			public Value(boolean val) {
				type = DATA_TYPE.BOOLEAN;
				this.bVal = val;
			}

			public boolean equals(Object o) {
				if (o == null || (!(o instanceof Value)))
					return false;
				Value v = (Value) o;
				return v.type == this.type && v.bVal == this.bVal && v.dVal == this.dVal && v.iVal == this.iVal
						&& ((v.sVal != null) ? v.sVal.equals(sVal) : true);
			}
		}

	}

	static public void loadFriends() {
		people.clear();
		try {
			Connection c = Main.plugin.getSQL().openConnection();
			Statement s = c.createStatement();
			s.execute(
					"CREATE TABLE IF NOT EXISTS `requests_disabled` ( `player` VARCHAR(36) NOT NULL, `disabled` TINYINT(1), UNIQUE KEY (player) )");
			ResultSet rs = s.executeQuery("SELECT `player`, `disabled` FROM `requests_disabled`");
			while (rs.next()) {
				Person p = getAdd(UUID.fromString(rs.getString(1)));
				p.requestsDisabled = rs.getBoolean(2);
			}
			s.execute(
					"CREATE TABLE IF NOT EXISTS `friends` ( `p1` VARCHAR(36) NOT NULL , `p2` VARCHAR(36) NOT NULL)");
			rs = s.executeQuery("SELECT `p1`, `p2` FROM `friends`");
			while (rs.next()) {
				String s1 = rs.getString(1), s2 = rs.getString(2);
				getAdd(UUID.fromString(s1)).friends.add(UUID.fromString(s2));
				getAdd(UUID.fromString(s2)).friends.add(UUID.fromString(s1));
			}
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.WARNING, "[FriendAPI] Cannot open mysql connection");
			e.printStackTrace();
		}
	}

	private static void updateDB(Person p, boolean requestsDisabled) {

		try {
			Connection c = Main.plugin.getSQL().openConnection();
			PreparedStatement ps = c.prepareStatement(
					"INSERT INTO `requests_disabled`(`player`, `disabled`) VALUES (?,?) ON DUPLICATE KEY UPDATE disabled=?");
			ps.setString(1, p.uuid.toString());
			ps.setInt(2, requestsDisabled ? 1 : 0);
			ps.setInt(3, requestsDisabled ? 1 : 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateDBAdd(UUID p1, UUID p2) {

		try {
			Connection c = Main.plugin.getSQL().openConnection();
			PreparedStatement ps = c.prepareStatement("INSERT INTO `friends`(`p1`, `p2`) VALUES (?,?)");
			ps.setString(1, p1.toString());
			ps.setString(2, p2.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateDBRemove(UUID p1, UUID p2) {

		try {
			Connection c = Main.plugin.getSQL().openConnection();
			PreparedStatement ps = c.prepareStatement("DELETE FROM `friends` WHERE (p1=? AND p2=?) OR (p1=? AND p2=?)");
			ps.setString(1, p1.toString());
			ps.setString(2, p2.toString());
			ps.setString(3, p2.toString());
			ps.setString(4, p1.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
