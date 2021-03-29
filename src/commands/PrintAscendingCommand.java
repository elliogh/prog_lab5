package commands;

/**
 * Класс команды print_ascending
 */
public class PrintAscendingCommand extends Command{
    public PrintAscendingCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.printAscending();
    }
}
