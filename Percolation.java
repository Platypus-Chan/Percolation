public class Percolation {
    private int[][] grid;
    private int numOfOpen;
    private int dimension;
    private boolean percolated;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        dimension = n;
        grid = new int[n][n];
        numOfOpen = 0;
        percolated = false;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // row, col starts from 1
    public void open(int row, int col) {
        checkDimension(row, col);
        if (grid[row-1][col-1] == 0) {
            grid[row-1][col-1] = 1;
            numOfOpen++;

            propagateFull(row-1, col-1);

        }
    }

    // row, col starts from 1
    public boolean isOpen(int row, int col) {
        checkDimension(row, col);
        return grid[row-1][col-1] != 0;
    }

    // row, col starts from 1
    public boolean isFull(int row, int col) {
        checkDimension(row, col);
        return grid[row-1][col-1] == 2;
    }

    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {
        return percolated;
    }

    private void checkDimension(int row, int col) {
        if (row < 1 || col < 1 || row > dimension || col > dimension)
            throw new IllegalArgumentException();
    }

    // here, the (row, col) are actual array indices
    private void propagateFull(int row, int col) {
        if (row < 0 || col < 0 || row >= dimension || col >= dimension || grid[row][col] == 0 || grid[row][col] == 2) {
            return;
        }

        boolean turned = false;
        // grid[row][col] only checks if the site is 1
        // if a site is open and on the first row, it is full
        if (row == 0) {
            grid[row][col] = 2;
            turned = true;
        }

        // if this open site is connected to any full site, than it is full
        if (row - 1 >= 0 && grid[row - 1][col] == 2) {
            grid[row][col] = 2;
            turned = true;
        }

        if (col - 1 >= 0 && grid[row][col - 1] == 2) {
            grid[row][col] = 2;
            turned = true;
        }

        if (row + 1 < dimension && grid[row + 1][col] == 2) {
            grid[row][col] = 2;
            turned = true;
        }

        if (col + 1 < dimension && grid[row][col + 1] == 2) {
            grid[row][col] = 2;
            turned = true;
        }

        // if turned, continue to propagate the full status
        if (turned) {
            if (row == dimension -1)
                percolated = true;

            propagateFull(row-1, col);
            propagateFull(row+1, col);
            propagateFull(row, col-1);
            propagateFull(row, col+1);
        }
    }
}