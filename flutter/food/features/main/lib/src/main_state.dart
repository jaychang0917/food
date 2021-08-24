import 'package:base/base.dart';

@immutable
class MainState extends Equatable {
  MainState({
    required this.activeTab
  });

  final AppTab activeTab;

  MainState copyWith({
    AppTab? activeTab
  }) {
    return MainState(
      activeTab: activeTab ?? this.activeTab,
    );
  }

  @override
  List<Object?> get props => [activeTab];
}

enum AppTab {
  home, search, order, profile
}
