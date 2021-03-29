package commands;

/**
 * Класс команды show
 */
public class ShowCommand extends Command{
    public ShowCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.show();
    }
}
