
import 'package:base/base.dart';

abstract class CountrySelectionEvent {}

class Initialized extends CountrySelectionEvent {}

class CountrySelected extends CountrySelectionEvent {
  CountrySelected(this.country);

  final Country country;
}

class SearchKeywordChanged extends CountrySelectionEvent {
  SearchKeywordChanged(this.text);

  final String text;
}
