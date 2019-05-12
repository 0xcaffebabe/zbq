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

    @Override
    public int hashCode(){
        return 10*pageNumber+20*length;
    }

    public static Page of(int pageNumber,int length){

        return new Page(pageNumber,length);
    }

}
