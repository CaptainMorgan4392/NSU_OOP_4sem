package Blocks;

import Exceptions.WrongParamsQuantityException;

import java.io.IOException;
import java.util.ArrayList;

public interface Block {
    enum InOutParam {
        DEFAULT,

        IN,
        OUT,
        IN_OUT
    }

    void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, IOException;

    InOutParam getParamOfBlock();
}
