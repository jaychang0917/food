
import 'package:base/base.dart';

abstract class PhoneNumberEvent {}

class Initialized extends PhoneNumberEvent {}

class PhoneTextChanged extends PhoneNumberEvent {
  PhoneTextChanged(this.phoneNumber);

  final String phoneNumber;
}

class NextButtonClicked extends PhoneNumberEvent {
  NextButtonClicked(this.phoneNumber);
  
  final String phoneNumber;
}

class CountryUpdated extends PhoneNumberEvent {
  CountryUpdated(this.country);

  final Country country;
}
