package commands;

/**
 * Класс команды update
 */
public class UpdateCommand extends Command{
    public UpdateCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            commandManager.receiver.update(id);
        } catch (Exception e) {
            System.out.println("Неверное значение ключа. Попробуйте ввести целое число");
        }

    }
}
