"""
import matplotlib.pyplot as plt
import csv


def read(file_name):
    rectangles_count, build_time, request_time, total_time = [], [], [], []
    with open("C:/Users/HUAWEI/IdeaProjects/algorithms_lab2/artefacts/" + file_name) as file:
        file_reader = csv.reader(file, delimiter=";")
        file.readline()
        for row in file_reader:
            rectangles_count.append(int(row[0]))
            build_time.append(int(row[1]))
            request_time.append(int(row[2]))
            total_time.append(int(row[3]))
    return rectangles_count, build_time, request_time, total_time


def draw_graph(rectangles_count, brute_force_time, map_time, tree_time, title):
    plt.xscale('log'), plt.yscale('log')
    plt.plot(rectangles_count[1:], brute_force_time[1:])
    plt.plot(rectangles_count[1:-2], map_time[1:])
    plt.plot(rectangles_count[1:], tree_time[1:])
    plt.legend(['brute force', 'map', 'tree'])
    plt.title(title)
    plt.xlabel('rectangles count (log)'), plt.ylabel('time, nanoseconds (log)')
    plt.show()


rectangles_count_, brute_force_build_time, brute_force_request_time, brute_force_total_time = \
    read("BruteForceAlgorithmResults.csv")
map_build_time, map_request_time, map_total_time = read("MapAlgorithmResults.csv")[1:]
tree_build_time, tree_request_time, tree_total_time = read("TreeAlgorithmResults.csv")[1:]

draw_graph(rectangles_count_, brute_force_build_time, map_build_time, tree_build_time, "build time")
draw_graph(rectangles_count_, brute_force_request_time, map_request_time, tree_request_time, "request time")
draw_graph(rectangles_count_, brute_force_total_time, map_total_time, tree_total_time, "total time")
"""
