package Blocks;

import Exceptions.WrongParamsQuantityException;

import java.io.IOException;
import java.util.ArrayList;

public interface Block {
    void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, IOException;
}
