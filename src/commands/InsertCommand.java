package commands;

/**
 * Класс команды insert
 */
public class InsertCommand extends Command{
    public InsertCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            commandManager.receiver.insert(id);
        } catch (Exception e) {
            System.out.println("Неверное значение ключа. Попробуйте ввести целое число");
        }
    }
}
