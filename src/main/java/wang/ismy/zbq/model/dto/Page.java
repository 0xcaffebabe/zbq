package wang.ismy.zbq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author my
 */
@Data
public class Page {

    private int pageNumber;

    private int length;

    public Page(int pageNumber, int length) {
        this.pageNumber = pageNumber;
        this.length = length;
    }

    public Page(){

    }

    public static Page of(int pageNumber,int length){

        return new Page(pageNumber,length);
    }

}
