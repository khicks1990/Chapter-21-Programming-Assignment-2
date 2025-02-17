import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

// AVLTree class represents an AVL tree data structure
class AVLTree {
    // Inner class representing a node in the AVL tree
    class AVLNode {
        int value;     // Value of the node
        AVLNode left;  // Left child
        AVLNode right; // Right child
        int height;    // Height of the node

        // Constructor to create a node with a value
        public AVLNode(int value) {
            this(value, null, null); // Calls another constructor with null children
        }

        // Constructor to create a node with a value and children
        public AVLNode(int val, AVLNode left1, AVLNode right1) {
            value = val;
            left = left1;
            right = right1;
            height = 0; // Height initialized to 0
        }

        // Resets the height of the node based on its children
        void resetHeight() {
            int leftHeight = AVLTree.getHeight(left);   // Height of the left child
            int rightHeight = AVLTree.getHeight(right); // Height of the right child
            height = 1 + Math.max(leftHeight, rightHeight); // Height is 1 plus the max of children heights
        }
    }

    // Returns the height of a given node, or -1 if null
    static int getHeight(AVLNode tree) {
        if (tree == null) return -1; // Height of null is -1
        return tree.height;          // Return the height of the node
    }

    // Adds a value to the AVL tree
    public boolean add(int x) {
        root = add(root, x); // Calls the recursive add function
        return true; // Always returns true for this implementation
    }

    // Returns the level (depth) of a specified value in the tree
    public int level(int x) {
        return level(root, x); // Calls the recursive level function
    }

    // Recursive method to find the level of a value
    private int level(AVLNode bTree, int x) {
        // If tree is empty, value not found

        // Search in the left subtree

        // Search in the right subtree
        
        // If value not found in either subtree
    }

    // Returns a visual representation of the AVL tree
    public JComponent getView() {
        return BTreeDisplay.createBTreeDisplay(root); // Calls method to create tree display
    }

    // Checks if the tree is empty
    public boolean isEmpty() {
        return root == null; // Returns true if root is null
    }

    // Root of the AVL tree
    private AVLNode root = null;

    // Recursive method to add a value and maintain the AVL properties
    private AVLNode add(AVLNode bTree, int x) {
        if (bTree == null) return new AVLNode(x); // If tree is empty, insert new node
        if (x < bTree.value)
            bTree.left = add(bTree.left, x); // Insert in left subtree
        else
            bTree.right = add(bTree.right, x); // Insert in right subtree

        bTree.resetHeight(); // Update the height of the current node

        // Check if the tree is balanced
        int leftHeight = getHeight(bTree.left);
        int rightHeight = getHeight(bTree.right);
        if (Math.abs(leftHeight - rightHeight) == 2)
            return balance(bTree); // Balance the tree if needed
        else
            return bTree; // Return the (possibly unbalanced) node
    }

    // Inner class to hold the result of a remove operation
    private class RemovalResult {
        AVLNode node; // The removed node
        AVLNode tree; // The resulting tree after removal

        RemovalResult(AVLNode node, AVLNode tree) {
            this.node = node;
            this.tree = tree;
        }
    }

    // Removes a value from the AVL tree
    public boolean remove(int x) {
        RemovalResult result = remove(root, x); // Call recursive remove
        if (result == null) return false; // Value not found
        root = result.tree; // Update the root of the tree
        return true; // Return true indicating successful removal
    }

    // Recursive method to remove a value
    private RemovalResult remove(AVLNode bTree, int x) {
            //add your code from assignment 1 here
              return new RemovalResult(null, null);  
    }

    // Removes the largest node from the subtree
    private RemovalResult removeLargest(AVLNode bTree) {
        if (bTree == null) return null; // If tree is empty, return null
        if (bTree.right == null) { // Largest node found
            AVLNode tree = bTree.left; // Get the left child
            bTree.left = null; // Clear the largest node
            return new RemovalResult(bTree, tree); // Return the result
        }
        RemovalResult remResult = removeLargest(bTree.right); // Search in right subtree
        bTree.right = remResult.tree; // Update right child
        bTree.resetHeight(); // Update height
        remResult.tree = balance(bTree); // Balance the tree if necessary
        return remResult; // Return the result
    }

    // Balances the subtree rooted at the given node
    private AVLNode balance(AVLNode bTree) {
        int rHeight = getHeight(bTree.right); // Height of right child
        int lHeight = getHeight(bTree.left);  // Height of left child
        if (Math.abs(rHeight - lHeight) <= 1) return bTree; // Already balanced

        // Right-heavy case
        if (rHeight > lHeight) {
            AVLNode rightChild = bTree.right; // Right child
            int rrHeight = getHeight(rightChild.right); // Right-right height
            int rlHeight = getHeight(rightChild.left);   // Right-left height
            if (rrHeight > rlHeight) return rrBalance(bTree); // Right-right case
            else {
                bTree = rlBalance(bTree); // Right-left case
                bTree.left = balance(bTree.left); // Balance left subtree
                bTree.right = balance(bTree.right); // Balance right subtree
                bTree.resetHeight(); // Update height
                return bTree; // Return balanced tree
            }
        } else { // Left-heavy case
            AVLNode leftChild = bTree.left; // Left child
            int llHeight = getHeight(leftChild.left); // Left-left height
            int lrHeight = getHeight(leftChild.right); // Left-right height
            if (llHeight > lrHeight) return llBalance(bTree); // Left-left case
            else {
                bTree = lrBalance(bTree); // Left-right case
                bTree.left = balance(bTree.left); // Balance left subtree
                bTree.right = balance(bTree.right); // Balance right subtree
                bTree.resetHeight(); // Update height
                return bTree; // Return balanced tree
            }
        }
    }

    // Right-right rotation
    private AVLNode rrBalance(AVLNode bTree) {
        AVLNode rightChild = bTree.right; // Right child
        AVLNode rightLeftChild = rightChild.left; // Left child of right
        rightChild.left = bTree; // Rotate right
        bTree.right = rightLeftChild; // Attach left child of right
        bTree.resetHeight(); // Update height
        rightChild.resetHeight(); // Update height
        return rightChild; // Return new root
    }

    // Right-left rotation
    private AVLNode rlBalance(AVLNode bTree) {
        AVLNode rNode = bTree.right; // Right child
        AVLNode rlNode = rNode.left; // Left child of right
        AVLNode rlrTree = rlNode.right; // Right child of left child
        AVLNode rllTree = rlNode.left; // Left child of left child

        // Perform rotation
        rNode.left = rlrTree;
        bTree.right = rllTree;
        rlNode.left = bTree;
        rlNode.right = rNode;

        // Update heights
        rNode.resetHeight();
        bTree.resetHeight();
        rlNode.resetHeight();
        return rlNode; // Return new root
    }

    // Left-left rotation
    private AVLNode llBalance(AVLNode bTree) {
        AVLNode leftChild = bTree.left; // Left child
        AVLNode lrTree = leftChild.right; // Right child of left
        leftChild.right = bTree; // Rotate left
        bTree.left = lrTree; // Attach right child of left
        bTree.resetHeight(); // Update height
        leftChild.resetHeight(); // Update height
        return leftChild; // Return new root
    }

    // Left-right rotation
    private AVLNode lrBalance(AVLNode bTree) {
        AVLNode lNode = bTree.left; // Left child
        AVLNode lrNode = lNode.right; // Right child of left
        AVLNode lrlTree = lrNode.left; // Left child of right child
        AVLNode lrrTree = lrNode.right; // Right child of right child

        // Perform rotation
        lNode.right = lrlTree;
        bTree.left = lrrTree;
        lrNode.left = lNode;
        lrNode.right = bTree;

        // Update heights
        lNode.resetHeight();
        bTree.resetHeight();
        lrNode.resetHeight();
        return lrNode; // Return new root
    }
}

// BTreeDisplay class provides a graphical representation of the AVL tree
class BTreeDisplay {
    // Creates a JPanel to visually represent the AVL tree
    static JPanel createBTreeDisplay(AVLTree.AVLNode node) {
        if (node == null) return new JPanel(); // Return empty panel for null node

        JTextField valueField = new JTextField(String.valueOf(node.value)); // Create text field for node value
        valueField.setEditable(false); // Make text field non-editable
        valueField.setHorizontalAlignment(JTextField.CENTER); // Center align text
        valueField.setPreferredSize(new Dimension(50, 25)); // Set preferred size

        JPanel nodePanel = new JPanel(new BorderLayout()); // Panel for the node
        nodePanel.add(valueField, BorderLayout.NORTH); // Add value field to the top
        nodePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set border for padding

        // If the node has children, create child panels
        if (node.left != null || node.right != null) {
            JPanel childrenPanel = new JPanel(new BorderLayout()); // Panel for children
            childrenPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set border

            // Recursively create displays for children
            childrenPanel.add(createBTreeDisplay(node.left), BorderLayout.WEST); // Left child
            childrenPanel.add(createBTreeDisplay(node.right), BorderLayout.EAST); // Right child
            childrenPanel.add(new JPanel(), BorderLayout.CENTER); // Center panel

            nodePanel.add(childrenPanel, BorderLayout.CENTER); // Add children panel to node panel
        }

        return nodePanel; // Return the constructed node panel
    }
}

// Main class to run the AVL Tree application
public class AVLLevel extends JFrame {
    private AVLTree avlTree = new AVLTree(); // Instance of AVLTree
    private JTextField cmdResultTextField; // Text field to display command results
    private JTextField cmdTextField; // Text field for user input commands
    private JPanel treePanel; // Panel to display the AVL tree

    // Constructor to initialize the UI
    public AVLLevel() {
        initializeUI(); // Call method to set up the user interface
    }

    // Method to set up the user interface
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setTitle("AVL Tree - Swing Implementation"); // Set window title
        setLayout(new BorderLayout(5, 5)); // Set layout with gaps
        setPreferredSize(new Dimension(800, 600)); // Set preferred size of window

        // Command help area
        JTextArea cmdTextArea = new JTextArea(
            "Available commands:\n" +
            "  add <value>\n" +
            "  remove <value>\n" +
            "  level <value>"
        );
        cmdTextArea.setEditable(false); // Make command help non-editable
        add(new JScrollPane(cmdTextArea), BorderLayout.NORTH); // Add command help to the top

        // Tree display area
        treePanel = new JPanel(new BorderLayout()); // Panel for displaying the tree
        add(new JScrollPane(treePanel), BorderLayout.CENTER); // Add tree panel in center

        // Command input area
        JPanel bottomPanel = new JPanel(); // Panel for command input
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Result display panel
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for result text
        resultPanel.add(new JLabel("Command Result: ")); // Label for command result
        cmdResultTextField = new JTextField(20); // Text field for displaying result
        cmdResultTextField.setEditable(false); // Make result field non-editable
        resultPanel.add(cmdResultTextField); // Add result field to result panel

        // Command input panel
        JPanel cmdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for command input
        cmdPanel.add(new JLabel("Command: ")); // Label for command input
        cmdTextField = new JTextField(20); // Text field for user input
        cmdTextField.addActionListener(new ActionListener() { // Add action listener for user input
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCommand(); // Handle command when Enter is pressed
            }
        });
        cmdPanel.add(cmdTextField); // Add command field to command panel

        // Assemble bottom panel
        bottomPanel.add(resultPanel); // Add result panel
        bottomPanel.add(cmdPanel); // Add command panel
        add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the south of the frame

        pack(); // Pack components to fit the window
        setLocationRelativeTo(null); // Center the window
    }

    // Method to handle user commands
    private void handleCommand() {
        String cmdStr = cmdTextField.getText().trim(); // Get input command
        cmdTextField.setText(""); // Clear command field
        Scanner sc = new Scanner(cmdStr); // Create scanner for command parsing
        if (!sc.hasNext()) return; // If no input, return

        String cmd = sc.next(); // Get the command type
        if (!sc.hasNextInt()) {
            cmdResultTextField.setText("Invalid command"); // Check for integer value
            return;
        }

        int value = sc.nextInt(); // Get the integer value from command
        switch (cmd.toLowerCase()) { // Handle commands
            case "add":
                avlTree.add(value); // Add value to the tree
                updateTreeView(); // Update visual representation
                cmdResultTextField.setText("Added " + value); // Show result
                break;
            case "remove":
                if (avlTree.remove(value)) { // Remove value from the tree
                    updateTreeView(); // Update visual representation
                    cmdResultTextField.setText("Removed " + value); // Show result
                } else {
                    cmdResultTextField.setText("Value not found"); // Value not found
                }
                break;
            case "level":
                int level = avlTree.level(value); // Get the level of the value
                cmdResultTextField.setText(level >= 0 ? 
                    "Level: " + level : "Value not found"); // Show result
                break;
            default:
                cmdResultTextField.setText("Invalid command"); // Invalid command
        }
    }

    // Updates the tree view after changes
    private void updateTreeView() {
        treePanel.removeAll(); // Clear the current tree panel
        JComponent view = avlTree.getView(); // Get new tree view
        treePanel.add(view); // Add new view to the panel
        treePanel.revalidate(); // Revalidate the panel to ensure it lays out its components correctly
        treePanel.repaint(); // Request a repaint to update the display with the new view
    }

    // Main method to run the AVLTree application
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure that the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new AVLLevel().setVisible(true)); // Create an instance of AVLLevel and make it visible
    }
}