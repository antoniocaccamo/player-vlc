package it.antanysavage.sm.player;


import it.antanysavage.sm.player.bundle.LocaleManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * This class demonstrates TableTreeViewer.
 */
public class NewsManager extends ApplicationWindow {
	// The TableTreeViewer
	private TableTreeViewer ttv;
	private Player player;
	private CTabFolder newsTabFolder;

	/**
	 * PlayerTableTree constructor
	 */
	public NewsManager(Player player) {
		super(null);
		this.
		player = player;
	}

	/**
	 * Runs the application
	 */
	public void run() {
		// Don't return from open() until window closes
		setBlockOnOpen(true);

		// Open the main window
		open();

		// Dispose the display
		Display.getCurrent().dispose();
	}

	/**
	 * Configures the shell
	 * 
	 * @param shell
	 *            the shell
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText( LocaleManager.getText(LocaleManager.APP_TITLE) + " | " + "News Manager");
		shell.setImage(Player.LOGO_IMAGE);
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param parent
	 *            the main window
	 * @return Control
	 */
	protected Control createContents(Composite parent) {
		
		newsTabFolder = new CTabFolder(parent, SWT.NONE);
		
		CTabItem cTabItem1 = new CTabItem(newsTabFolder, SWT.NONE);
		cTabItem1.setText("News Manager #1");
		
		newsTabFolder.setSelection(0);
		
		Composite composite = new Composite(newsTabFolder, SWT.NONE);
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.makeColumnsEqualWidth = true;
		composite.setLayout(compositeLayout);

		// Create the table viewer to display the players
		
		GridData ttvLData = new GridData();
		ttvLData.grabExcessHorizontalSpace = true;
		ttvLData.horizontalAlignment = GridData.FILL;
		ttvLData.verticalAlignment = GridData.FILL;
		ttvLData.grabExcessVerticalSpace = true;
		ttv = new TableTreeViewer(composite);
		ttv.getControl().setLayoutData(ttvLData);

		cTabItem1.setControl(composite);	
		

		// Set the content and label providers
		ttv.setContentProvider(new PlayerTreeContentProvider());
		ttv.setLabelProvider(new PlayerTreeLabelProvider());
		ttv.setInput(new PlayerTableModel());

		// Set up the table
		Table table = ttv.getTableTree().getTable();
		new TableColumn(table, SWT.LEFT).setText("First Name");
		new TableColumn(table, SWT.LEFT).setText("Last Name");
		new TableColumn(table, SWT.RIGHT).setText("Points");
		new TableColumn(table, SWT.RIGHT).setText("Rebounds");
		new TableColumn(table, SWT.RIGHT).setText("Assists");

		// Expand everything
		ttv.expandAll();

		// Pack the columns
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {			
			table.getColumn(i).pack();
		}

		// Turn on the header and the lines
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Pack the window
		parent.pack();
		parent.setSize(225, 213);

		// Scroll to top
		ttv.reveal(ttv.getElementAt(0));

		return ttv.getTableTree();
	}

	/**
	 * The application entry point
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new NewsManager(null).run();
	}

	public Player getSmPlayer() {
		return player;
	}
	
	public boolean close() {
		getShell().setVisible(false);
		return false;
	}

}

/**
 * This class provides the content for the TableTreeViewer in PlayerTableTree
 */

class PlayerTreeContentProvider implements ITreeContentProvider {
	private static final Object[] EMPTY = new Object[] {};

	/**
	 * Gets the children for a team or player
	 * 
	 * @param arg0
	 *            the team or player
	 * @return Object[]
	 */
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof Team)
			return ((Team) arg0).getPlayers().toArray();
		// Players have no children . . . except Shawn Kemp
		return EMPTY;
	}

	/**
	 * Gets the parent team for a player
	 * 
	 * @param arg0
	 *            the player
	 * @return Object
	 */
	public Object getParent(Object arg0) {
		return ((NPlayer) arg0).getTeam();
	}

	/**
	 * Gets whether this team or player has children
	 * 
	 * @param arg0
	 *            the team or player
	 * @return boolean
	 */
	public boolean hasChildren(Object arg0) {
		return getChildren(arg0).length > 0;
	}

	/**
	 * Gets the elements for the table
	 * 
	 * @param arg0
	 *            the model
	 * @return Object[]
	 */
	public Object[] getElements(Object arg0) {
		// Returns all the teams in the model
		return ((PlayerTableModel) arg0).teams;
	}

	/**
	 * Disposes any resources
	 */
	public void dispose() {
		// We don't create any resources, so we don't dispose any
	}

	/**
	 * Called when the input changes
	 * 
	 * @param arg0
	 *            the parent viewer
	 * @param arg1
	 *            the old input
	 * @param arg2
	 *            the new input
	 */
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// Nothing to do
	}
}

/**
 * This class provides the labels for the PlayerTableTree application
 */

class PlayerTreeLabelProvider extends PlayerLabelProvider {
	/**
	 * Gets the image for the specified column
	 * 
	 * @param arg0
	 *            the player or team
	 * @param arg1
	 *            the column
	 * @return Image
	 */
	public Image getColumnImage(Object arg0, int arg1) {
		// Teams have no image
		if (arg0 instanceof Player)
			return super.getColumnImage(arg0, arg1);
		return null;
	}

	/**
	 * Gets the text for the specified column
	 * 
	 * @param arg0
	 *            the player or team
	 * @param arg1
	 *            the column
	 * @return String
	 */
	public String getColumnText(Object arg0, int arg1) {
		if (arg0 instanceof NPlayer)
			return super.getColumnText(arg0, arg1);
		Team team = (Team) arg0;
		return arg1 == 0 ? team.getYear() + " " + team.getName() : "";
	}
}

/**
 * This class contains the data model for the PlayerTable
 */

class PlayerTableModel {
	public Team[] teams;

	/**
	 * Constructs a PlayerTableModel Fills the model with data
	 */
	public PlayerTableModel() {
		teams = new Team[3];

		teams[0] = new Team("Celtics", "1985-86");
		teams[0].add(new NPlayer("Larry", "Bird", 25.8f, 9.8f, 6.8f));
		teams[0].add(new NPlayer("Kevin", "McHale", 21.3f, 8.1f, 2.7f));
		teams[0].add(new NPlayer("Robert", "Parish", 16.1f, 9.5f, 1.8f));
		teams[0].add(new NPlayer("Dennis", "Johnson", 15.6f, 3.4f, 5.8f));
		teams[0].add(new NPlayer("Danny", "Ainge", 10.7f, 2.9f, 5.1f));
		teams[0].add(new NPlayer("Scott", "Wedman", 8.0f, 2.4f, 1.1f));
		teams[0].add(new NPlayer("Bill", "Walton", 7.6f, 6.8f, 2.1f));
		teams[0].add(new NPlayer("Jerry", "Sichting", 6.5f, 1.3f, 2.3f));
		teams[0].add(new NPlayer("David", "Thirdkill", 3.3f, 1.4f, 0.3f));
		teams[0].add(new NPlayer("Sam", "Vincent", 3.2f, 0.8f, 1.2f));
		teams[0].add(new NPlayer("Sly", "Williams", 2.8f, 2.5f, 0.3f));
		teams[0].add(new NPlayer("Rick", "Carlisle", 2.6f, 1.0f, 1.4f));
		teams[0].add(new NPlayer("Greg", "Kite", 1.3f, 2.0f, 1.3f));

		teams[1] = new Team("Bulls", "1995-96");
		teams[1].add(new NPlayer("Michael", "Jordan", 30.4f, 6.6f, 4.3f));
		teams[1].add(new NPlayer("Scottie", "Pippen", 19.4f, 6.4f, 5.9f));
		teams[1].add(new NPlayer("Toni", "Kukoc", 13.1f, 4.0f, 3.5f));
		teams[1].add(new NPlayer("Luc", "Longley", 9.1f, 5.1f, 1.9f));
		teams[1].add(new NPlayer("Steve", "Kerr", 8.4f, 1.3f, 2.3f));
		teams[1].add(new NPlayer("Ron", "Harper", 7.4f, 2.7f, 2.6f));
		teams[1].add(new NPlayer("Dennis", "Rodman", 5.5f, 14.9f, 2.5f));
		teams[1].add(new NPlayer("Bill", "Wennington", 5.3f, 2.5f, 0.6f));
		teams[1].add(new NPlayer("Jack", "Haley", 5.0f, 2.0f, 0.0f));
		teams[1].add(new NPlayer("John", "Salley", 4.4f, 3.3f, 1.3f));
		teams[1].add(new NPlayer("Jud", "Buechler", 3.8f, 1.5f, 0.8f));
		teams[1].add(new NPlayer("Dickey", "Simpkins", 3.6f, 2.6f, 0.6f));
		teams[1].add(new NPlayer("James", "Edwards", 3.5f, 1.4f, 0.4f));
		teams[1].add(new NPlayer("Jason", "Caffey", 3.2f, 1.9f, 0.4f));
		teams[1].add(new NPlayer("Randy", "Brown", 2.7f, 1.0f, 1.1f));

		teams[2] = new Team("Lakers", "1987-1988");
		teams[2].add(new NPlayer("Magic", "Johnson", 23.9f, 6.3f, 12.2f));
		teams[2].add(new NPlayer("James", "Worthy", 19.4f, 5.7f, 2.8f));
		teams[2].add(new NPlayer("Kareem", "Abdul-Jabbar", 17.5f, 6.7f, 2.6f));
		teams[2].add(new NPlayer("Byron", "Scott", 17.0f, 3.5f, 3.4f));
		teams[2].add(new NPlayer("A.C.", "Green", 10.8f, 7.8f, 1.1f));
		teams[2].add(new NPlayer("Michael", "Cooper", 10.5f, 3.1f, 4.5f));
		teams[2].add(new NPlayer("Mychal", "Thompson", 10.1f, 4.1f, 0.8f));
		teams[2].add(new NPlayer("Kurt", "Rambis", 5.7f, 5.8f, 0.8f));
		teams[2].add(new NPlayer("Billy", "Thompson", 5.6f, 2.9f, 1.0f));
		teams[2].add(new NPlayer("Adrian", "Branch", 4.3f, 1.7f, 0.5f));
		teams[2].add(new NPlayer("Wes", "Matthews", 4.2f, 0.9f, 2.0f));
		teams[2].add(new NPlayer("Frank", "Brickowski", 4.0f, 2.6f, 0.3f));
		teams[2].add(new NPlayer("Mike", "Smrek", 2.2f, 1.1f, 0.1f));
	}
}

/**
 * This class represents a player
 */

class NPlayer {
	private Team team;

	private String lastName;

	private String firstName;

	private float points;

	private float rebounds;

	private float assists;

	/**
	 * Constructs an empty Player
	 */
	public NPlayer() {
		this(null, null, 0.0f, 0.0f, 0.0f);
	}

	/**
	 * Constructs a Player
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param points
	 *            the points
	 * @param rebounds
	 *            the rebounds
	 * @param assists
	 *            the assists
	 */
	public NPlayer(String firstName, String lastName, float points,
			float rebounds, float assists) {
		setFirstName(firstName);
		setLastName(lastName);
		setPoints(points);
		setRebounds(rebounds);
		setAssists(assists);
	}

	/**
	 * Sets the team for theo player
	 * 
	 * @param team
	 *            the team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Gets the assists
	 * 
	 * @return float
	 */
	public float getAssists() {
		return assists;
	}

	/**
	 * Sets the assists
	 * 
	 * @param assists
	 *            The assists to set.
	 */
	public void setAssists(float assists) {
		this.assists = assists;
	}

	/**
	 * Gets the first name
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name
	 * 
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name
	 * 
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the points
	 * 
	 * @return float
	 */
	public float getPoints() {
		return points;
	}

	/**
	 * Sets the points
	 * 
	 * @param points
	 *            The points to set.
	 */
	public void setPoints(float points) {
		this.points = points;
	}

	/**
	 * Gets the rebounds
	 * 
	 * @return float
	 */
	public float getRebounds() {
		return rebounds;
	}

	/**
	 * Sets the rebounds
	 * 
	 * @param rebounds
	 *            The rebounds to set.
	 */
	public void setRebounds(float rebounds) {
		this.rebounds = rebounds;
	}

	/**
	 * Gets the team
	 * 
	 * @return Team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Returns whether this player led the team in the specified category
	 * 
	 * @param column
	 *            the column (category)
	 * @return boolean
	 */
	public boolean ledTeam(int column) {
		return team.led(this, column);
	}
}
/**
 * This class represents a team
 */

class Team {
	private String name;

	private String year;

	private List players;

	/**
	 * Constructs a Team
	 * 
	 * @param name
	 *            the name
	 * @param year
	 *            the year
	 */
	public Team(String name, String year) {
		this.name = name;
		this.year = year;
		players = new LinkedList();
	}

	/**
	 * Gets the name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the year
	 * 
	 * @return String
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Adds a player
	 * 
	 * @param player
	 *            the player to add
	 * @return boolean
	 */
	public boolean add(NPlayer player) {
		boolean added = players.add(player);
		if (added)
			player.setTeam(this);
		return added;
	}

	/**
	 * Gets the players
	 * 
	 * @return List
	 */
	public List getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * Returns whether the specified player led his team in the specified
	 * category
	 * 
	 * @param nPlayer
	 *            the player
	 * @param column
	 *            the category
	 * @return boolean
	 */
	public boolean led(NPlayer nPlayer, int column) {
		boolean led = true;

		// Go through all the players on the team, comparing the specified
		// player's
		// stats with each other player.
		for (int i = 0, n = players.size(); i < n && led; i++) {
			NPlayer test = (NPlayer) players.get(i);
			if (nPlayer == test)
				continue;
			switch (column) {
			case PlayerConst.COLUMN_POINTS:
				if (nPlayer.getPoints() < test.getPoints())
					led = false;
				break;
			case PlayerConst.COLUMN_REBOUNDS:
				if (nPlayer.getRebounds() < test.getRebounds())
					led = false;
				break;
			case PlayerConst.COLUMN_ASSISTS:
				if (nPlayer.getAssists() < test.getAssists())
					led = false;
				break;
			}
		}
		return led;
	}
}

/**
 * This class contains constants for the PlayerTable application
 */

class PlayerConst {
	// Column constants
	public static final int COLUMN_FIRST_NAME = 0;

	public static final int COLUMN_LAST_NAME = 1;

	public static final int COLUMN_POINTS = 2;

	public static final int COLUMN_REBOUNDS = 3;

	public static final int COLUMN_ASSISTS = 4;
}

/**
 * This class provides the labels for PlayerTable
 */

class PlayerLabelProvider implements ITableLabelProvider {
	// Image to display if the player led his team
	private Image ball;

	// Constructs a PlayerLabelProvider
	public PlayerLabelProvider() {
		// Create the image
		try {
			ball = new Image(null, new FileInputStream("images/ball.png"));
		} catch (FileNotFoundException e) {
			// Swallow it
		}
	}

	/**
	 * Gets the image for the specified column
	 * 
	 * @param arg0
	 *            the player
	 * @param arg1
	 *            the column
	 * @return Image
	 */
	public Image getColumnImage(Object arg0, int arg1) {
		NPlayer player = (NPlayer) arg0;
		Image image = null;
		switch (arg1) {
		// A player can't lead team in first name or last name
		case PlayerConst.COLUMN_POINTS:
		case PlayerConst.COLUMN_REBOUNDS:
		case PlayerConst.COLUMN_ASSISTS:
			if (player.ledTeam(arg1))
				// Set the image
				image = ball;
			break;
		}
		return image;
	}

	/**
	 * Gets the text for the specified column
	 * 
	 * @param arg0
	 *            the player
	 * @param arg1
	 *            the column
	 * @return String
	 */
	public String getColumnText(Object arg0, int arg1) {
		NPlayer player = (NPlayer) arg0;
		String text = "";
		switch (arg1) {
		case PlayerConst.COLUMN_FIRST_NAME:
			text = player.getFirstName();
			break;
		case PlayerConst.COLUMN_LAST_NAME:
			text = player.getLastName();
			break;
		case PlayerConst.COLUMN_POINTS:
			text = String.valueOf(player.getPoints());
			break;
		case PlayerConst.COLUMN_REBOUNDS:
			text = String.valueOf(player.getRebounds());
			break;
		case PlayerConst.COLUMN_ASSISTS:
			text = String.valueOf(player.getAssists());
			break;
		}
		return text;
	}

	/**
	 * Adds a listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void addListener(ILabelProviderListener arg0) {
		// Throw it away
	}

	/**
	 * Dispose any created resources
	 */
	public void dispose() {
		// Dispose the image
		if (ball != null)
			ball.dispose();
	}

	/**
	 * Returns whether the specified property, if changed, would affect the
	 * label
	 * 
	 * @param arg0
	 *            the player
	 * @param arg1
	 *            the property
	 * @return boolean
	 */
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	/**
	 * Removes the specified listener
	 * 
	 * @param arg0
	 *            the listener
	 */
	public void removeListener(ILabelProviderListener arg0) {
		// Do nothing
	}
}
