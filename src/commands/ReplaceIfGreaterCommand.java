package commands;

/**
 * Класс команды replace_if_greater
 */
public class ReplaceIfGreaterCommand extends Command{
    public ReplaceIfGreaterCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            commandManager.receiver.replaceIfGreater(id);
        } catch (Exception e) {
            System.out.println("Неверное значение ключа. Попробуйте ввести целое число");
        }

    }
}
