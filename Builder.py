from kivy.app import App
from kivy.uix.anchorlayout import AnchorLayout
from kivy.uix.widget import Widget


class Builder(AnchorLayout):
    pass


class BuilderApp(App):
    def build(self):
        return Builder()


if __name__ == '__main__':
    BuilderApp().run()
