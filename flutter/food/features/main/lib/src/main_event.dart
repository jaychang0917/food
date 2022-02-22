import 'main_state.dart';

abstract class MainEvent {}

class Initialized extends MainEvent {}

class TabChanged extends MainEvent {
  TabChanged(this.tab);

  final AppTab tab;
}
