package VideoPokerGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Class {@link PayTable} extends Class {@link JTable} and represents the type of table used in Video Poker GUI.
 * <p>
 * By using this class, one table of type {@link PayTable} will have already a set of dimensions and properties that are different
 * from the default of {@link JTable} Class. Apart from these changes, some methods of {@link JTable} are overridden to adapt to 
 * the desired specifications.
 */
@SuppressWarnings("serial")
public class PayTable extends JTable{

	public PayTable(String[][] data, String[] columns) {
		super(data, columns);
		
		this.setShowGrid(true);
		this.setBackground(SystemColor.menu);
		this.setFont(new Font("Arial", Font.PLAIN, 14));        
		this.setShowHorizontalLines(false);
		this.setRowSelectionAllowed(false);
		this.setFocusable(false);        
        this.setBounds(17, 45, 818, 175);
	}
	
	
	/**
	 * For a row and column returns a {@link Boolean} with false, disabling the edition property of a table.
	 * @param row an {@link Integer} correspondent to a row of a table.
	 * @param row an {@link Integer} correspondent to a column of a table.
	 * @return {@link Boolean} with false.
	 */
	@Override
    public boolean isCellEditable(int row, int column) {                
            return false;               
    }
	
	/**
	 * For a column of a table, sets the background colour to yellow, adding the default background to the previous
	 * yellow column in the table.
	 * @param previndex an {@link Integer} with the previous yellow column in the table.
	 * @param index an {@link Integer} with the column to fill with yellow background in the table.
	 */
	public void highlightCol(int previndex, int index){
		// Column to highlight 
		TableColumn colToHighligh = this.getColumnModel().getColumn(index);
		
		// Define new style and default style
        CellHighlighterRenderer cellRenderer = new CellHighlighterRenderer();
        TableCellRenderer defaultRenderer = colToHighligh.getCellRenderer();
		
		// update table style
        if (index > 0){
        	colToHighligh.setCellRenderer(cellRenderer);
        }
		colToHighligh = this.getColumnModel().getColumn(previndex);
		colToHighligh.setCellRenderer(defaultRenderer);
	
		this.repaint();
	}

	/**
	 * Class {@link CellHighlighterRenderer} extends Class {@link DefaultTableCellRenderer}, a standard class for rendering (displaying)
	 *  individual cells in a JTable.
	 * <p>
	 * Used in {@link #highlightCol(int, int) highlightCol} to change the rendering of a column by changing the background to yellow.
	 */
	public class CellHighlighterRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		    Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		   
		    cell.setBackground(Color.YELLOW);
		    
		    return cell;
		}
	}	
}
