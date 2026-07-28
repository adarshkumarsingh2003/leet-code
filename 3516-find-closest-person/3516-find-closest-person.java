class Solution {
    public int findClosest(int x, int y, int z) {
        int x_win = Math.abs(z-x);
        int y_win = Math.abs(z-y);
        if(x_win < y_win){
            return 1;
        }
        else if(x_win>y_win){
            return 2;
        }
        return 0;
    }
}