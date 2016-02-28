package com.den13l.new2048;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LineShiftTest {

    @Test
    public void leftShift_singleShift() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(1);
        Cell cell2 = new Cell(2);
        Cell cell3 = new Cell(3, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(1, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(3, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());
    }

    @Test
    public void leftShift_withMerge() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(1, 2);
        Cell cell2 = new Cell(2, 2);
        Cell cell3 = new Cell(3);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(1, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(2, shift1SourceCell.getPosition());
        assertEquals(0, shift1DestCell.getPosition());
    }

    @Test
    public void leftShift_withoutMerge() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(1, 2);
        Cell cell2 = new Cell(2, 4);
        Cell cell3 = new Cell(3);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(1, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(2, shift1SourceCell.getPosition());
        assertEquals(1, shift1DestCell.getPosition());
    }

    @Test
    public void leftShift_sameThree() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(1, 2);
        Cell cell2 = new Cell(2, 2);
        Cell cell3 = new Cell(3, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(3, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(1, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(2, shift1SourceCell.getPosition());
        assertEquals(0, shift1DestCell.getPosition());

        Shift shift2 = shiftList.get(2);
        Cell shift2SourceCell = shift2.getSourceCell();
        Cell shift2DestCell = shift2.getDestCell();
        assertEquals(3, shift2SourceCell.getPosition());
        assertEquals(1, shift2DestCell.getPosition());
    }

    @Test
    public void topShift_singleShift() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(4);
        Cell cell2 = new Cell(8);
        Cell cell3 = new Cell(12, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(1, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(12, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());
    }

    @Test
    public void topShift_withMerge() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(4, 2);
        Cell cell2 = new Cell(8, 2);
        Cell cell3 = new Cell(12);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(4, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(8, shift1SourceCell.getPosition());
        assertEquals(0, shift1DestCell.getPosition());
    }

    @Test
    public void topShift_withoutMerge() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(4, 2);
        Cell cell2 = new Cell(8, 4);
        Cell cell3 = new Cell(12);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(4, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(8, shift1SourceCell.getPosition());
        assertEquals(4, shift1DestCell.getPosition());
    }

    @Test
    public void topShift_sameThree() throws Exception {
        Cell cell0 = new Cell(0);
        Cell cell1 = new Cell(4, 2);
        Cell cell2 = new Cell(8, 2);
        Cell cell3 = new Cell(12, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(3, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(4, shift0SourceCell.getPosition());
        assertEquals(0, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(8, shift1SourceCell.getPosition());
        assertEquals(0, shift1DestCell.getPosition());

        Shift shift2 = shiftList.get(2);
        Cell shift2SourceCell = shift2.getSourceCell();
        Cell shift2DestCell = shift2.getDestCell();
        assertEquals(12, shift2SourceCell.getPosition());
        assertEquals(4, shift2DestCell.getPosition());
    }

    @Test
    public void rightShift_singleShift() throws Exception {
        Cell cell0 = new Cell(3);
        Cell cell1 = new Cell(2);
        Cell cell2 = new Cell(1);
        Cell cell3 = new Cell(0, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(1, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(0, shift0SourceCell.getPosition());
        assertEquals(3, shift0DestCell.getPosition());
    }

    @Test
    public void rightShift_withMerge() throws Exception {
        Cell cell0 = new Cell(3);
        Cell cell1 = new Cell(2, 2);
        Cell cell2 = new Cell(1, 2);
        Cell cell3 = new Cell(0);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(2, shift0SourceCell.getPosition());
        assertEquals(3, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(1, shift1SourceCell.getPosition());
        assertEquals(3, shift1DestCell.getPosition());
    }

    @Test
    public void rightShift_withoutMerge() throws Exception {
        Cell cell0 = new Cell(3);
        Cell cell1 = new Cell(2, 2);
        Cell cell2 = new Cell(1, 4);
        Cell cell3 = new Cell(0);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(2, shift0SourceCell.getPosition());
        assertEquals(3, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(1, shift1SourceCell.getPosition());
        assertEquals(2, shift1DestCell.getPosition());
    }

    @Test
    public void rightShift_sameThree() throws Exception {
        Cell cell0 = new Cell(3);
        Cell cell1 = new Cell(2, 2);
        Cell cell2 = new Cell(1, 2);
        Cell cell3 = new Cell(0, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(3, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(2, shift0SourceCell.getPosition());
        assertEquals(3, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(1, shift1SourceCell.getPosition());
        assertEquals(3, shift1DestCell.getPosition());

        Shift shift2 = shiftList.get(2);
        Cell shift2SourceCell = shift2.getSourceCell();
        Cell shift2DestCell = shift2.getDestCell();
        assertEquals(0, shift2SourceCell.getPosition());
        assertEquals(2, shift2DestCell.getPosition());
    }

    @Test
    public void bottomShift_singleShift() throws Exception {
        Cell cell0 = new Cell(12);
        Cell cell1 = new Cell(8);
        Cell cell2 = new Cell(4);
        Cell cell3 = new Cell(0, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(1, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(0, shift0SourceCell.getPosition());
        assertEquals(12, shift0DestCell.getPosition());
    }

    @Test
    public void bottomShift_withMerge() throws Exception {
        Cell cell0 = new Cell(12);
        Cell cell1 = new Cell(8, 2);
        Cell cell2 = new Cell(4, 2);
        Cell cell3 = new Cell(0);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(8, shift0SourceCell.getPosition());
        assertEquals(12, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(4, shift1SourceCell.getPosition());
        assertEquals(12, shift1DestCell.getPosition());
    }

    @Test
    public void bottomShift_withoutMerge() throws Exception {
        Cell cell0 = new Cell(12);
        Cell cell1 = new Cell(8, 2);
        Cell cell2 = new Cell(4, 4);
        Cell cell3 = new Cell(0);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(2, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(8, shift0SourceCell.getPosition());
        assertEquals(12, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(4, shift1SourceCell.getPosition());
        assertEquals(8, shift1DestCell.getPosition());
    }

    @Test
    public void bottomShift_sameThree() throws Exception {
        Cell cell0 = new Cell(12);
        Cell cell1 = new Cell(8, 2);
        Cell cell2 = new Cell(4, 2);
        Cell cell3 = new Cell(0, 2);

        List<Cell> cellList = new ArrayList<>();
        cellList.add(cell0);
        cellList.add(cell1);
        cellList.add(cell2);
        cellList.add(cell3);

        System.out.println("cellList: " + cellList);
        LineShift lineShift = new LineShift(cellList);
        List<Shift> shiftList = lineShift.getShiftList();
        System.out.println("shiftList: " + shiftList.toString());
        assertEquals(3, shiftList.size());

        Shift shift0 = shiftList.get(0);
        Cell shift0SourceCell = shift0.getSourceCell();
        Cell shift0DestCell = shift0.getDestCell();
        assertEquals(8, shift0SourceCell.getPosition());
        assertEquals(12, shift0DestCell.getPosition());

        Shift shift1 = shiftList.get(1);
        Cell shift1SourceCell = shift1.getSourceCell();
        Cell shift1DestCell = shift1.getDestCell();
        assertEquals(4, shift1SourceCell.getPosition());
        assertEquals(12, shift1DestCell.getPosition());

        Shift shift2 = shiftList.get(2);
        Cell shift2SourceCell = shift2.getSourceCell();
        Cell shift2DestCell = shift2.getDestCell();
        assertEquals(0, shift2SourceCell.getPosition());
        assertEquals(8, shift2DestCell.getPosition());
    }
}