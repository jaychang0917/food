
abstract class PasswordEvent {}

class PasswordTextChanged extends PasswordEvent {
  PasswordTextChanged(this.password);

  final String password;
}

class NextButtonClicked extends PasswordEvent {
  NextButtonClicked(this.password);
  
  final String password;
}
