package wang.ismy.zbq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Page {

    private int pageNumber;

    private int length;

    public Page(int pageNumber, int length) {
        this.pageNumber = pageNumber;
        this.length = length;
    }

    public Page(){

    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
