import 'package:base/base.dart';

import 'main_event.dart';
import 'main_state.dart';

class MainBloc extends Bloc<MainEvent, MainState> {
  MainBloc({required AppTab activeTab}) : super(MainState(activeTab: activeTab));

  @override
  Stream<MainState> mapEventToState(MainEvent event) async* {
    if (event is TabChanged) {
      yield* _mapTabChangedToState(event);
    }
  }

  Stream<MainState> _mapTabChangedToState(TabChanged event) async* {
    yield state.copyWith(activeTab: event.tab);
  }
}
